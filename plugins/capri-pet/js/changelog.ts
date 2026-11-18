import type { ChangelogEntry } from '../../../shared/changelog'

/** Newest first. Every version users got gets an entry; see shared/changelog.tsx. */
export const CHANGELOG: ChangelogEntry[] = [
	{
		version: '2.10.37',
		date: '2026-11-19',
		title: 'Sounds and voice-call mode removed',
		changes: [
			'Removed sound effects, volume and the During voice calls mode completely, in the app and in the plugin code. She is silent and behaves the same in voice calls.',
			'The Sounds & reactions page is now just Reactions, and the sound credits are gone.',
		],
	},
	{
		version: '2.10.36',
		date: '2026-11-18',
		title: 'Shorter sounds and reactions page',
		changes: [
			'Removed the Sounds, Volume and During voice calls sections from Sounds & reactions. Only Reacts to is left.',
		],
	},
	{
		version: '2.10.35',
		date: '2026-11-18',
		title: 'Shorter animation tester',
		changes: [
			'Removed Jump, Hang, Charging, Low battery, Fidget, Look around and Bubbles from the animation tester.',
		],
	},
	{
		version: '2.10.34',
		date: '2026-11-18',
		title: 'Possess a bit faster',
		changes: [
			'The possess edge bolt and torn bars change 12 times a second, and the sliding rows 24 times a second.',
		],
	},
	{
		version: '2.10.33',
		date: '2026-11-18',
		title: 'Calmer possess',
		changes: [
			'The possess effect jitters less: the edge bolt and torn bars change 8 times a second, there are fewer bars, the bolt is less jagged and fewer rows slide sideways.',
		],
	},
	{
		version: '2.10.32',
		date: '2026-11-18',
		title: 'Calmer dizzy wave',
		changes: [
			'The dizzy wave is smaller and slower, and only moves her hair. Her face and legs stay still.',
		],
	},
	{
		version: '2.10.31',
		date: '2026-11-18',
		title: 'Dizzy wave',
		changes: [
			'Her dizzy hair now ripples: the peaks rise and sag in a wave that travels along her, with a slight sideways sway.',
		],
	},
	{
		version: '2.10.30',
		date: '2026-11-18',
		title: 'Dizzy glitter',
		changes: [
			'Her dizzy form is covered in dense white and pink glitter that reshuffles every frame, like in the show.',
		],
	},
	{
		version: '2.10.29',
		date: '2026-11-18',
		title: 'No white dots in her hair',
		changes: [
			'Removed the white glitter dots from her hair in every animation, idle included.',
		],
	},
	{
		version: '2.10.28',
		date: '2026-11-18',
		title: 'No white dots on happy pose',
		changes: [
			'Removed the twinkling white dots from her happy pose.',
		],
	},
	{
		version: '2.10.27',
		date: '2026-11-18',
		title: 'Glitchy happy rays',
		changes: [
			'Her happy rays are fewer, in her red, purple and pink, with hard pixel edges.',
			'They glitch: kinked, flickering and tearing sideways on every tick.',
		],
	},
	{
		version: '2.10.26',
		date: '2026-11-18',
		title: 'Sharper happy pose',
		changes: [
			'Her happy pose is no longer blurry: it uses her idle sprite colours and the same dark edge, so it looks like the rest of her.',
		],
	},
	{
		version: '2.10.25',
		date: '2026-11-18',
		title: 'Glitch out of the happy pose',
		changes: [
			'When her happy pose ends she glitches back into her idle model with a row tear, instead of snapping.',
		],
	},
	{
		version: '2.10.24',
		date: '2026-11-18',
		title: 'Happy pose traced from the art',
		changes: [
			'Her happy pose is now the figure from the victory art itself, not her idle model: red hair mass with glitter, thin dark V arms, thin red and green legs.',
			'She glitches into the pose with a row tear, then holds it with glitter that keeps twinkling.',
		],
	},
	{
		version: '2.10.23',
		date: '2026-11-18',
		title: 'Happy arms like the art',
		changes: [
			'Her happy arms are redrawn from the victory art: thin dark arms with a purple edge rise from her shoulders in a V, hands with spiky fingers at the sides of her hair.',
		],
	},
	{
		version: '2.10.22',
		date: '2026-11-18',
		title: 'Happy: hands up, hair shards',
		changes: [
			'No more face banner. When she is happy she throws both arms up and out with her hands spread, and shards of her hair fly off with the rays.',
		],
	},
	{
		version: '2.10.21',
		date: '2026-11-18',
		title: 'Happy burst',
		changes: [
			'When Capri is happy, rays of white, red and purple burst out behind her and a slanted banner with her face slides in and out beside her, at 24 fps.',
		],
	},
	{
		version: '2.10.20',
		date: '2026-11-18',
		title: '24 fps everywhere',
		changes: [
			'Everything of hers plays at 24 frames a second: her strips have as many frames as the same time needs, and the star strike and possession redraw 24 times a second.',
		],
	},
	{
		version: '2.10.19',
		date: '2026-11-18',
		title: 'Star and possession at 30 fps',
		changes: [
			'The star strike and the possession redraw their lightning, glitch bars and bolts 30 times a second.',
		],
	},
	{
		version: '2.10.18',
		date: '2026-11-18',
		title: '30 frames',
		changes: [
			'Her animations are 30 frames each instead of 60: still much smoother than before, and about half the size.',
		],
	},
	{
		version: '2.10.17',
		date: '2026-11-18',
		title: '60 frames',
		changes: [
			'Her idle, itch, sit, voice, lie down, stretch, licks, jump, hurt, spin, dizzy, sleep, wake and star pose all have 60 frames now. Timing is the same, motion is much smoother.',
		],
	},
	{
		version: '2.10.16',
		date: '2026-11-18',
		title: 'Smoother idle',
		changes: [
			'Her idle has 72 frames instead of 24, played at 24 fps: same loop, much smoother sway, hair and breathing.',
		],
	},
	{
		version: '2.10.15',
		date: '2026-11-18',
		title: 'No freeze in the star pose',
		changes: [
			'The star pose raises her arm once, then the hold frames loop, so she no longer freezes on the last frame.',
		],
	},
	{
		version: '2.10.14',
		date: '2026-11-17',
		title: 'Outline setting back',
		changes: [
			'The outline setting is back on the Pet page.',
		],
	},
	{
		version: '2.10.13',
		date: '2026-11-17',
		title: 'Only Capri',
		changes: [
			'Capri is the only pet: the picker and pack settings are gone, and a saved choice of another pet turns back into her.',
			'Star-calling pose has 24 frames: a slower raise, a long hold with flutter and twinkling.',
		],
	},
	{
		version: '2.10.12',
		date: '2026-11-17',
		title: 'Star pose, more detail',
		changes: [
			'Pointing arm redrawn with an elbow, forearm pattern, red band, a hand with fingers and a twinkling glint and crackle at her fingertips.',
		],
	},
	{
		version: '2.10.11',
		date: '2026-11-17',
		title: 'Calling the star',
		changes: [
			'When she calls the star she turns toward it and points her arm up at it.',
		],
	},
	{
		version: '2.10.10',
		date: '2026-11-17',
		title: 'Glitch into her face',
		changes: [
			'Sleeping and waking no longer break into blocks: she glitches into her face and back, with torn rows, purple and green ghosts and colour tear lines.',
		],
	},
	{
		version: '2.10.9',
		date: '2026-11-17',
		title: 'Waking up',
		changes: [
			'Waking up: her eyes open, then the shards fly back together into her.',
		],
	},
	{
		version: '2.10.8',
		date: '2026-11-17',
		title: 'Sleep form',
		changes: [
			'When she sleeps her hair and body break into shards and she becomes just her face: the green rectangle with the hook, white pointer and shut eyes, floating and bobbing.',
		],
	},
	{
		version: '2.10.7',
		date: '2026-11-17',
		title: 'Choom',
		changes: [
			'Capri calls you "choom" in lots of her lines, like she does in the show.',
		],
	},
	{
		version: '2.10.6',
		date: '2026-11-17',
		title: 'Capri talks like Capri',
		changes: [
			'New lines for every moment, in her voice: glitchy, chaotic, playful, thrill-seeking and friendly. She uses them instead of the generic pet lines.',
		],
	},
	{
		version: '2.10.5',
		date: '2026-11-17',
		title: 'Dizzy size',
		changes: [
			'Her dizzy pose is drawn bigger, so lying down she is as long as she is tall standing.',
		],
	},
	{
		version: '2.10.4',
		date: '2026-11-17',
		title: 'Spin down, then dizzy',
		changes: [
			'Spin her up while dragging and let go: she keeps spinning, slows down, stops upright, then gets dizzy.',
		],
	},
	{
		version: '2.10.3',
		date: '2026-11-17',
		title: 'Dizzy: back to 2.10.0',
		changes: [
			'Dizzy pose is back to the 2.10.0 version.',
		],
	},
	{
		version: '2.10.2',
		date: '2026-11-17',
		title: 'Dizzy: traced from the footage',
		changes: [
			'Dizzy pose redone to match the footage shape for shape: tall glittery hair mass on one side, two big smooth peaks, small green-masked head with the white pointer, black body strips and green leg marks on a purple floor line.',
		],
	},
	{
		version: '2.10.1',
		date: '2026-11-17',
		title: 'Dizzy: like the footage',
		changes: [
			'Dizzy pose redrawn: she lies face-up on the floor, small green mask at one end, a wide mass of jagged red glittering hair spread over her.',
		],
	},
	{
		version: '2.10.0',
		date: '2026-11-17',
		title: 'Dizzy: collapsed',
		changes: [
			'When dizzy she now collapses flat on the floor, hair spread over her, breathing and twitching, with little stars circling her head.',
		],
	},
	{
		version: '2.9.0',
		date: '2026-11-17',
		title: 'Flying pose is her idle art',
		changes: [
			'The flying pose is now built from her real idle pieces (same hair, face, body, arms and legs), tilted with the legs trailing and bent and one arm reaching, still animated.',
		],
	},
	{
		version: '2.8.1',
		date: '2026-11-17',
		title: 'Flying pose legs, her art',
		changes: [
			'In the flying pose both legs now follow her art: bright green thighs, black lower legs and heels with a thin red rim.',
		],
	},
	{
		version: '2.8.0',
		date: '2026-11-17',
		title: 'Smoother appear and leave',
		changes: [
			'Appearing and leaving have 19 frames instead of 7: two dissolve frames with torn rows between each of her teleport frames.',
		],
	},
	{
		version: '2.7.9',
		date: '2026-11-17',
		title: 'Flying pose hair',
		changes: [
			'The flying pose hair now uses her idle reds: flat saturated red, no pale pink highlights.',
		],
	},
	{
		version: '2.7.8',
		date: '2026-11-17',
		title: 'Flying pose legs',
		changes: [
			'In the flying pose one leg is now black with green stripes and the other red, like her idle.',
		],
	},
	{
		version: '2.7.7',
		date: '2026-11-17',
		title: 'Flying pose details',
		changes: [
			'The flying pose gets more of her idle design: dark slashes through the hair, green stripes down both legs and green marks at the neck.',
		],
	},
	{
		version: '2.7.6',
		date: '2026-11-17',
		title: 'Old flying pose back',
		changes: [
			'Her flying pose is back to the earlier drawing in her idle colours, still animated.',
		],
	},
	{
		version: '2.7.4',
		date: '2026-11-17',
		title: 'Fewer hair specks',
		changes: [
			'The flying pose has far fewer white specks in her hair.',
		],
	},
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
