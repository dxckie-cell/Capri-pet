import type { ChangelogEntry } from '../../../shared/changelog'

/** Newest first. Every version users got gets an entry; see shared/changelog.tsx. */
export const CHANGELOG: ChangelogEntry[] = [
	{
		version: '2.7.3',
		date: '2026-11-17',
		title: 'Flying pose animated',
		changes: [
			'Her flying pose now moves like her idle: hair flutters, legs swing from the hip, the arm drifts and she bobs and tilts (16 frames).',
		],
	},
	{
		version: '2.7.2',
		date: '2026-11-16',
		title: 'Flying pose, idle look',
		changes: [
			'Her flying pose is back, now drawn in her idle look: the same colours, starry hair and purple outline.',
		],
	},
	{
		version: '2.7.1',
		date: '2026-11-16',
		title: 'Flying pose like idle',
		changes: [
			'Her flying pose (Spin, dragged, flying) is now her idle look, lifted and swaying, instead of the tilted drawing.',
		],
	},
	{
		version: '2.7.0',
		date: '2026-11-16',
		title: 'Flying pose when carried',
		changes: [
			'Dragging her now uses the flying pose, and she faces the way you are carrying her. Spin and flying turn to face their direction too.',
		],
	},
	{
		version: '2.6.2',
		date: '2026-11-16',
		title: 'Clean spin',
		changes: [
			'Spin shows only her flying pose; the small slash drawn over her is gone.',
		],
	},
	{
		version: '2.6.1',
		date: '2026-11-16',
		title: 'Spin pose',
		changes: [
			'Spin now uses her flying pose, with a small tilt and bob, under the lightning slash.',
		],
	},
	{
		version: '2.6.0',
		date: '2026-11-16',
		title: 'Her speech bubble',
		changes: [
			'Chat text now looks like her cards: a dark panel with cut corners and a purple edge, her portrait, CAPRI / NIGHT-CITY with a green tag, and what she says in bold.',
		],
	},
	{
		version: '2.5.2',
		date: '2026-11-16',
		title: 'Intro removed',
		changes: [
			'The intro that played when Discord opened is gone.',
		],
	},
	{
		version: '2.5.1',
		date: '2026-11-16',
		title: 'Intro, more frames',
		changes: [
			'The intro is smoother: it punches in from larger, frames dissolve into each other, the wave sways and bobs faster, and red/cyan colour-split ghosts settle on each change.',
		],
	},
	{
		version: '2.5.0',
		date: '2026-11-16',
		title: 'Intro',
		changes: [
			'Her intro plays once each time Discord opens: arms fly in, her face, a wave, then it fades. It tears sideways in glitchy bands.',
		],
	},
	{
		version: '2.4.2',
		date: '2026-11-16',
		title: 'Glitchy star strike',
		changes: [
			'The star strike is pixelated and glitchy like her bolt, tearing hardest as the bolt hits.',
		],
	},
	{
		version: '2.4.1',
		date: '2026-11-16',
		title: 'Star strike, more frames',
		changes: [
			'The star strike has more animation: lines drawing between stars, glints running up the trail, rings charging round her, a crackling bolt that changes every frame, a flash, shockwave rings and falling sparks.',
		],
	},
	{
		version: '2.4.0',
		date: '2026-11-16',
		title: 'Star strike',
		changes: [
			'The spell is replaced by a star strike: a faint constellation, a trail of sparkles to a bright star, then a lightning bolt that bursts it into rays and a crosshair.',
			'It no longer needs VFX, and the spell list page is gone from Fun.',
		],
	},
	{
		version: '2.3.4',
		date: '2026-11-16',
		title: 'Old edge back',
		changes: [
			'Possession brings back the thin red, purple and pink lightning edge. The eyes, mouth and glitch bars keep the pixel bolt look.',
		],
	},
	{
		version: '2.3.3',
		date: '2026-11-16',
		title: 'Possession in bolt style',
		changes: [
			'Possession now looks like her lightning bolt: heavy red jagged edge with a green offset, sparks, pixelated and glitching in bands.',
		],
	},
	{
		version: '2.3.2',
		date: '2026-11-16',
		title: 'Mouth in place',
		changes: [
			'The zigzag mouth now sits right under her eyes, smaller and thinner, like her face.',
		],
	},
	{
		version: '2.3.1',
		date: '2026-11-16',
		title: 'Possession face',
		changes: [
			'Possession now has her face: two big eyes in the middle and a laughing zigzag mouth, with small eyes around.',
		],
	},
	{
		version: '2.3.0',
		date: '2026-11-16',
		title: 'Possession',
		changes: [
			'Bubbles are replaced by Capri\'s possession: red lightning runs round the screen edge and eyes open across it, following her.',
			'The screen glitch option now tears red, purple and green bars across the screen.',
		],
	},
	{
		version: '2.2.3',
		date: '2026-11-16',
		title: 'Same bolt everywhere',
		changes: [
			'The animation tester and Spin now draw the same heavy red pixel slash as her real teleport, not the old thin bolt.',
		],
	},
	{
		version: '2.2.2',
		date: '2026-11-16',
		title: 'Bolt in the picker',
		changes: [
			'The animation tester now shows the lightning bolt for Walk (and Spin) instead of a blank gap.',
		],
	},
	{
		version: '2.2.1',
		date: '2026-11-16',
		title: 'Pixel lightning',
		changes: [
			'The lightning slash is pixelated with a green offset edge, sparks, forks and glitching bands again.',
		],
	},
	{
		version: '2.2.0',
		date: '2026-11-16',
		title: 'Lightning slash',
		changes: [
			'Capri shatters, a heavy red lightning bolt slashes across the screen to where she is going, and she re-forms there.',
		],
	},
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
