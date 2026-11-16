import { lookOf } from "../../native"
import { BOSS_PETS } from "../../bossPets"
import { FieldRow } from "../fieldGroup"
import { rowIcon } from "../icon"
import { RateSlider } from "../rateSlider"
import { useBottomPadding } from "../safeArea"
import { call, update, useSettings } from "../state"
import * as components from "@valenced/components"
import { Design } from "@valenced/discord/design"
import * as ReactNative from "react-native"

/** The extras: bubbles or rainbow puffs, star strike, treats, eating buttons, and buttons to try them. */
export default function Fun() {
	const { Page } = components
	const { ScrollView } = ReactNative
	const { Stack, TableRowGroup, TableRow, TableSwitchRow, TableRadioGroup, TableRadioRow, Slider, Text } = Design as any

	const s = useSettings()
	const bottom = useBottomPadding()
	const pet = lookOf(s.look)
	const nyan = false
	const boss = BOSS_PETS.find(b => b.id === s.look)
	const horseman = boss?.id === BOSS_PETS[0].id
	const name = s.name?.trim() || pet.defaultName
	const needsShow = s.show ? "" : ` Turn on Show ${name} first.`
	const bubbleChoice = !s.bubbles ? "off" : s.bubbleFx ? "fx" : "plain"

	return (
		<Page>
			<ScrollView contentContainerStyle={{ paddingBottom: bottom }}>
				<Stack spacing={24}>
					<RateSlider
						title="Treats"
						description={`A treat${nyan ? " (pop-tarts, cake, donuts, ice cream, fish, milk)" : ""} turns up: with Gravity on it drops to the floor, off it floats. Tap it and ${name} goes to eat it, which makes it very happy. Twice as often when it's lonely.`}
						on={s.treats}
						rate={s.treatRate}
						minSeconds={240}
						maxSeconds={540}
						onChange={(treats, treatRate) => update({ treats, treatRate })}
					/>

					<RateSlider
						title="Eats buttons and icons"
						description={`It gobbles a small button near it. Tap ${name} and it gives everything back. Nothing changes for real: the button only hides.`}
						on={s.eat}
						rate={s.eatRate}
						minSeconds={40}
						maxSeconds={120}
						onChange={(eat, eatRate) => update({ eat, eatRate })}
					/>
					<TableRowGroup hasIcons>
						<TableRow
							label="Give everything back"
							subLabel="Puts back anything it has eaten, straight away."
							icon={rowIcon("RefreshIcon", "ic_refresh")}
							onPress={() => call("giveBack")}
						/>
					</TableRowGroup>

					<TableRadioGroup
						key={`bubbles-${bubbleChoice}`}
						title="Possession"
						description="Now and then, and when she's happy, she dives into the screen: red lightning runs round its edge and eyes open all over it, watching her. With the screen glitch, bars of red, purple and green tear across it too."
						defaultValue={bubbleChoice}
						onChange={(value: string) => update({ bubbles: value !== "off", bubbleFx: value === "fx" })}
					>
						<TableRadioRow label="Off" value="off" />
						<TableRadioRow label="Possession" value="plain" />
						<TableRadioRow label="Possession and screen glitch" subLabel="Works on any Android. Works alongside VFX." value="fx" />
					</TableRadioGroup>

					<RateSlider
						title="Star strike"
						description="Now and then she calls down a star: a faint constellation, a trail of sparkles, then a lightning strike that bursts it."
						on={s.magic}
						rate={s.magicRate}
						minSeconds={60}
						maxSeconds={180}
						onChange={(magic, magicRate) => update({ magic, magicRate })}
					/>

					{boss ? (
						<RateSlider
							title="Mischief"
							description={
								horseman
									? `${name} swings the scythe at your screen and slices it apart for a moment, or throws it: it bounces round the screen and comes back to his hand.`
									: `${name} goes for your screen: the chain hooks it and drags it away, the claws tear it three ways, or a head-butt bulges and cracks it. Everything springs back.`
							}
							on={s.attacks}
							rate={s.attackRate}
							minSeconds={120}
							maxSeconds={300}
							onChange={(attacks, attackRate) => update({ attacks, attackRate })}
						/>
					) : null}
					{boss ? (
						<TableRowGroup hasIcons>
							<TableSwitchRow
								label="Screen tricks"
								subLabel="The slicing, hooking, tearing and cracking use a snapshot of the screen. Off, the moves still play (and the Horseman only throws). Never while the keyboard is up."
								icon={rowIcon("ImageIcon", "PlayIcon")}
								value={s.attackFx}
								onValueChange={(attackFx: boolean) => update({ attackFx })}
							/>
						</TableRowGroup>
					) : null}
					{horseman ? (
						<RateSlider
							title="The horse"
							description={`Now and then his horse wanders in along the floor and grazes a while. Tap it while ${name} is out and he rides it round the screen; tap him to get off.`}
							on={s.companion}
							rate={s.companionRate}
							minSeconds={1200}
							maxSeconds={2400}
							onChange={(companion, companionRate) => update({ companion, companionRate })}
						/>
					) : null}
					{horseman ? (
						<TableRowGroup>
							<FieldRow label={`Ride for ${s.rideMinutes} ${s.rideMinutes === 1 ? "minute" : "minutes"}`} description="Then he gets off and the horse trots away. Tap him any time to end it sooner.">
								<Slider step={1} value={s.rideMinutes} minimumValue={1} maximumValue={5} onValueChange={(v: number) => update({ rideMinutes: Math.round(v) })} />
							</FieldRow>
						</TableRowGroup>
					) : null}

					<TableRowGroup title="Try it" hasIcons>
						<TableRow
							label="Possess the screen now"
							subLabel={`See it without waiting.${needsShow}`}
							icon={rowIcon("PlayIcon")}
							disabled={!s.show}
							onPress={() => call("blow")}
						/>
						{boss ? (
							<TableRow
								label="Attack now"
								subLabel={s.show ? "A random weapon, straight away." : needsShow.trim()}
								icon={rowIcon("PlayIcon")}
								disabled={!s.show}
								onPress={() => call("attack")}
							/>
						) : null}
						{horseman ? (
							<TableRow
								label="Call the horse"
								subLabel={s.show ? "It walks in now. Tap it to ride." : needsShow.trim()}
								icon={rowIcon("PlayIcon")}
								disabled={!s.show}
								onPress={() => call("callHorse")}
							/>
						) : null}
						{horseman ? (
							<TableRow
								label="Ride now"
								subLabel={s.show ? "Straight onto the horse. Tap him to get off." : needsShow.trim()}
								icon={rowIcon("PlayIcon")}
								disabled={!s.show}
								onPress={() => call("ride")}
							/>
						) : null}
						<TableRow
							label="Call the star now"
							subLabel={`See it without waiting.${needsShow}`}
							icon={rowIcon("PlayIcon")}
							disabled={!s.show}
							onPress={() => call("castMagic")}
						/>
						<TableRow
							label="Drop a treat now"
							subLabel={s.show ? `Tap it and ${name} comes to eat it.` : needsShow.trim()}
							icon={rowIcon("PlayIcon")}
							disabled={!s.show}
							onPress={() => call("dropTreat")}
						/>
					</TableRowGroup>
				</Stack>
			</ScrollView>
		</Page>
	)
}
