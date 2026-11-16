import type { ChangelogEntry } from '../../../shared/changelog'

/** Newest first. Every version users got gets an entry; see shared/changelog.tsx. */
export const CHANGELOG: ChangelogEntry[] = [
	{
		version: '2.1.1',
		date: '2026-11-16',
		title: 'Smaller Capri, lightning spin',
		changes: [
			'Capri is about three times smaller at every size setting.',
			'Her spin is now a lightning bolt too.',
		],
	},
	{
		version: '2.1.0',
		date: '2026-11-16',
		title: 'Capri moves by lightning',
		changes: [
			'Capri no longer walks: she glitches out, streaks across as a red lightning bolt and reforms where she is going.',
		],
	},
	{
		version: '2.0.15',
		date: '2026-11-16',
		title: 'Capri stays in one piece',
		changes: [
			'Fixed her arm and legs tearing apart while she walks or runs.',
		],
	},
	{
		version: '2.0.14',
		date: '2026-11-16',
		title: 'Capri legs match her art',
		changes: [
			'Walk and run legs are cut from her own sprite and bent at the knee, so they keep her glitter, outline and green shards.',
		],
	},
	{
		version: '2.0.13',
		date: '2026-11-16',
		title: 'Capri really walks',
		changes: [
			'Walk and run redrawn with jointed legs: thighs swing, knees bend and feet lift, instead of a stiff shuffle.',
		],
	},
	{
		version: '2.0.12',
		date: '2026-11-16',
		title: 'Capri moves for everything',
		changes: [
			'Capri has her own animation for every movement: run, jump, flinch, happy, sleep, sit, sad, talk, stretch and idle fidgets.',
		],
	},
	{
		version: '2.0.11',
		date: '2026-11-16',
		title: 'Capri faces forward',
		changes: [
			'Capri now faces the direction she walks.',
		],
	},
	{
		version: '2.0.10',
		date: '2026-11-16',
		title: 'Capri moves all over',
		changes: [
			'Idle and walk now move her whole body: swaying torso, swinging arms, shifting legs and trailing hair.',
		],
	},
	{
		version: '2.0.9',
		date: '2026-11-16',
		title: 'Capri walks',
		changes: [
			'New walk animation: legs step, body bobs, hair trails behind.',
		],
	},
	{
		version: '2.0.8',
		date: '2026-11-16',
		title: 'Capri idles',
		changes: [
			'New idle animation: her hair sways, she breathes, the glitter shimmers and she glitches now and then.',
		],
	},
	{
		version: '2.0.7',
		date: '2026-11-15',
		title: 'Capri teleports',
		changes: [
			'Capri glitch-teleports away when she leaves and back in when she returns.',
		],
	},
	{
		version: '2.0.6',
		date: '2026-11-15',
		title: 'Capri, true to the art',
		changes: [
			'Capri now uses her real standing sprite: tall, thin, spiky glittery hair.',
		],
	},
	{
		version: '2.0.5',
		date: '2026-11-15',
		title: 'Capri, clearer face',
		changes: [
			'Capri redrawn as pixel art with her green diamond face, blue eyes, grin and big glittery hair.',
		],
	},
	{
		version: '2.0.4',
		date: '2026-11-15',
		title: 'New Capri sprites',
		changes: [
			'Capri redrawn from reference art: full hair and body instead of a clipped corner.',
		],
	},
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
