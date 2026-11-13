// Capri Pet for Valenced: a neon cyber-kitten that floats over the app and chases your finger.
// Port of choccychan's (OTKUSteyler) Capri Pet from chocos-plugins.

import type { JsonStorage } from '@valenced/json-storage'
import { getModules } from '@valenced/modules/finders'
import { withName } from '@valenced/modules/finders/filters'
import { after } from '@valenced/patcher'
import { createElement, Fragment } from 'react'
import PetOverlay, { PetHost, Safe } from './Pet'
import { newPet, type PetState, tick } from './pet'
import { toast } from './toast'

// Hosts that wrap the whole app (so touches can be watched and chased), best first,
// then a fallback that can only draw the pet.
const FULL_HOSTS = ['AppContainer', 'App']
const OVERLAY_HOSTS = ['AppToastContainer']

// One shared pet object: the overlay reads it live and `save` writes it to disk.
const pet: PetState = newPet()
let storage: JsonStorage<PetState> | undefined
let saveTimer: ReturnType<typeof setTimeout> | undefined

const save = () => {
	if (!storage) return
	clearTimeout(saveTimer)
	saveTimer = setTimeout(
		() => storage?.set({ ...pet, rev: undefined }, true),
		400,
	)
}

/** Turns one export into something `after` can patch: a class, function, memo() or forwardRef(). */
function targetOf(holder: any, key: string): [any, string] | undefined {
	let v: any
	try {
		v = holder[key]
	} catch {
		return
	}
	// Class components (React Native's AppContainer is one) are patched through their prototype.
	if (typeof v === 'function') {
		return typeof v.prototype?.render === 'function'
			? [v.prototype, 'render']
			: [holder, key]
	}
	if (v && typeof v === 'object') {
		if (typeof v.type === 'function') return [v, 'type']
		if (typeof v.render === 'function') return [v, 'render']
	}
}

/** Looks at the module itself, its default export, then every other export. */
function findPatchTarget(exports: any): [any, string] | undefined {
	if (typeof exports === 'function' && exports.prototype?.render)
		return [exports.prototype, 'render']
	if (exports && typeof exports === 'object') {
		if (typeof exports.type === 'function') return [exports, 'type']
		if (typeof exports.render === 'function') return [exports, 'render']
		let keys: string[] = []
		try {
			keys = [...new Set(['default', ...Object.keys(exports)])]
		} catch {}
		for (const key of keys) {
			const target = targetOf(exports, key)
			if (target) return target
		}
	}
}

/** A short description of an export, for the on-screen message. */
function describe(exports: any): string {
	let keys = ''
	try {
		keys = Object.keys(exports ?? {})
			.slice(0, 5)
			.join(',')
	} catch {}
	return `${typeof exports}${keys ? ` {${keys}}` : ''}`
}

export default plugin<{ jsonStorage: PetState }>({
	jsonStorage: { default: newPet(), load: true },

	start(api) {
		// Wrapping the app root only takes effect on the next mount, so enabling mid-session needs a reload.
		if (api.plugin.startedLate) {
			toast('Capri Pet: fully restart Discord to show her')
			api.plugin.requireReload()
			return
		}

		storage = api.jsonStorage
		api.cleanup(() => {
			clearTimeout(saveTimer)
			storage = undefined
		})

		// Load the saved pet and hand it to the already-mounted overlay.
		api.jsonStorage
			.get()
			.then(saved => {
				Object.assign(pet, saved)
				tick(pet)
				pet.rev = (pet.rev ?? 0) + 1
			})
			.catch(e => api.plugin.reportError(e))

		toast('Capri Pet: loaded')
		let hosted = false
		const missing = setTimeout(() => {
			if (!hosted) toast("Capri Pet: couldn't find the app to attach to")
		}, 8000)
		api.cleanup(() => clearTimeout(missing))
		const hook = (name: string, full: boolean) => {
			api.cleanup(
				getModules(withName<any>(name), exports => {
					if (hosted) return
					try {
						const target = findPatchTarget(exports)
						if (!target) {
							toast(
								`Capri Pet: found ${name} but can't patch it (${describe(exports)})`,
							)
							return
						}
						api.cleanup(
							after(target[0], target[1], (ret: any) => {
								// Never break the app: on any problem, return what Discord rendered.
								if (ret == null) return ret
								try {
									return full
										? createElement(PetHost, { pet, save }, ret)
										: createElement(
												Fragment,
												null,
												ret,
												createElement(
													Safe,
													null,
													createElement(PetOverlay, { pet, save }),
												),
											)
								} catch (e) {
									api.plugin.reportError(e)
									return ret
								}
							}),
						)
						hosted = true
						toast(`Capri Pet: attached to ${name}`)
					} catch (e) {
						api.plugin.reportError(e)
					}
				}),
			)
		}

		for (const n of FULL_HOSTS) hook(n, true)
		for (const n of OVERLAY_HOSTS) hook(n, false)
	},

	// Unpatching can't remove what Discord already rendered with the pet.
	stop(api) {
		api.plugin.requireReload()
	},
})
