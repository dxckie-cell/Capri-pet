package com.dxckie.capri

// Assembled Capri strips: sprite sheets built from reference art, split into parts under the JVM 64KB constant limit.
internal object CapriStrips {
    val idle get() = arrayOf(CapriStrip_Idle_P1.DATA)
    val sit get() = arrayOf(CapriStrip_Sit_P1.DATA + CapriStrip_Sit_P2.DATA)
    val sleep get() = arrayOf(CapriStrip_Sleep_P1.DATA)
    val talk get() = arrayOf(CapriStrip_Talk_P1.DATA + CapriStrip_Talk_P2.DATA)
    val curious get() = arrayOf(CapriStrip_Curious_P1.DATA + CapriStrip_Curious_P2.DATA)
    val victory get() = arrayOf(CapriStrip_Victory_P1.DATA + CapriStrip_Victory_P2.DATA)
    val alarm get() = arrayOf(CapriStrip_Alarm_P1.DATA)
    val flinch get() = arrayOf(CapriStrip_Flinch_P1.DATA + CapriStrip_Flinch_P2.DATA)
    val fall get() = arrayOf(CapriStrip_Fall_P1.DATA + CapriStrip_Fall_P2.DATA)
    val land get() = arrayOf(CapriStrip_Land_P1.DATA + CapriStrip_Land_P2.DATA)
    val shake get() = arrayOf(CapriStrip_Shake_P1.DATA + CapriStrip_Shake_P2.DATA)
}
