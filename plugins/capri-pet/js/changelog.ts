import type { ChangelogEntry } from '../../../shared/changelog'

/** Newest first. Every version users got gets an entry; see shared/changelog.tsx. */
export const CHANGELOG: ChangelogEntry[] = [
	{
		version: '2.0.3',
		date: '2026-11-15',
		title: 'Capri in the picker',
		changes: [
			'Capri now shows up in the Classic pets.',
		],
	},
	{
		version: '2.0.2',
		date: '2026-11-15',
		title: 'Capri joins the pets',
		changes: [
			'New pet: Capri, from Cyberpunk: Edgerunners 2 (fan art).',
		],
	},
	{
		version: '2.0.0',
		date: '2026-11-14',
		title: 'Capri Pet, now native',
		changes: [
			"Capri Pet is now a port of bleelblep's Sparkle: a native pet that lives on top of Discord, with the unicorn, six cats, six dogs, the Snowman, the Dragon and the Headless Horseman and Krampus boss pets.",
			'Tap, pet, feed, shake and throw it, and let it react to Discord.',
			'Nyan Cat, Gary and the paid holiday pets are left out.',
		],
	},
]
