package com.dxckie.capri

// Capri's strips; the image data is in CapriStrips.kt (generated).
internal object PetData {
	class Pet(override val id: String, override val name: String, override val w: Int, override val h: Int, override val strips: Map<String, Pair<Int, Array<String>>>) : SheetPet

	val pets: List<Pet> = listOf(
		Pet("capri", "Capri", 80, 140, mapOf(
		    "idle" to (72 to CapriStrips.idle),
		    "walk" to (17 to CapriStrips.walk),
		    "run" to (27 to CapriStrips.run),
		    "jump" to (14 to CapriStrips.jump),
		    "hurt" to (12 to CapriStrips.hurt),
		    "itch" to (24 to CapriStrips.itch),
		    "sleep" to (72 to CapriStrips.sleep),
		    "wake" to (24 to CapriStrips.wake),
		    "cast" to (37 to CapriStrips.cast),
		    "happy" to (24 to CapriStrips.happy),
		    "unhappy" to (8 to CapriStrips.unhappy),
		    "sit" to (30 to CapriStrips.sit),
		    "lay" to (18 to CapriStrips.lay),
		    "voice" to (24 to CapriStrips.voice),
		    "stretch" to (24 to CapriStrips.stretch),
		    "lick1" to (24 to CapriStrips.lick1),
		    "lick2" to (24 to CapriStrips.lick2),
		    "outro" to (19 to CapriStrips.outro),
		    "dizzy" to (36 to CapriStrips.dizzy)
		)),
	)
}
