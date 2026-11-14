import { useBottomPadding } from "../safeArea"
import * as components from "@valenced/components"
import { Design } from "@valenced/discord/design"
import * as ReactNative from "react-native"

/** Attribution, on its own route so the root page stays an index. */
export default function License() {
	const { Page } = components
	const { ScrollView } = ReactNative
	const { Stack, TableRowGroup, TableRow } = Design as any

	return (
		<Page>
			<ScrollView contentContainerStyle={{ paddingBottom: useBottomPadding() }}>
				<Stack spacing={24}>
					<TableRowGroup title="Licence">
						<TableRow label="Capri Pet" subLabel="A port of Sparkle (Shimeji) by bleelblep, CC0-1.0, public domain. Rebranded and trimmed by dxckie." />
					</TableRowGroup>
					<TableRowGroup title="Art">
						<TableRow label="Unicorn" subLabel="Sprites provided by the plugin's author." />
						<TableRow
							label="Cats and dogs"
							subLabel="Pet Cats Pack and Pet Dogs Pack by LuizMelo (luizmelo.itch.io). CC0, public domain."
						/>
						<TableRow
							label="Seasonal pets"
							subLabel="Flappy Dragon (bevouliin.com), OpenGameArt. CC0, public domain. Snowman Character by pechvogel, CC-BY 3.0."
						/>
					</TableRowGroup>
					<TableRowGroup title="Sounds">
						<TableRow
							label="Juhani Junkala"
							subLabel="The Essential Retro Video Game Sound Effects Collection. CC0, public domain."
						/>
						<TableRow label="Kenney" subLabel="Interface Sounds, Impact Sounds and Digital Audio. CC0, public domain." />
						<TableRow label="Mixkit" subLabel="The throw swoosh. Mixkit Sound Effects Free License." />
						<TableRow
							label="Kerzoven, AntumDeluge"
							subLabel="The cats' purrs and meows: Cat Purr & Meow and Kitten Mew, OpenGameArt. CC0, public domain."
						/>
						<TableRow label="Brandon Morris" subLabel="The dogs' barks: Dog barking mono, OpenGameArt. CC0, public domain." />
					</TableRowGroup>
					<TableRowGroup title="Credits">
						<TableRow
							label="Inspiration"
							subLabel={
								'The behaviour follows the exteraGram pet "Clawd" by @itskotovski: tapping, petting, throwing, peeking, shaking. This is a fresh implementation; no code or text from it is used.'
							}
						/>
					</TableRowGroup>
				</Stack>
			</ScrollView>
		</Page>
	)
}
