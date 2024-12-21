package nebula.LsuL1

import spinal.core._
import spinal.core.sim._
import spinal.lib._
import spinal.lib.misc.pipeline._

case class JumpCmd() extends Bundle {
  val address = UInt(64 bits)
}
trait throwPipe extends AreaObject {
  val jumpCmd = Flow(JumpCmd())
}

import spinal.core.sim._

object PC extends AreaObject {
  val PCVal = Payload(UInt(64 bits)) 
  val PCPLUS4 = Payload(UInt(64 bits))
  val VALIDCACHEREAD = Payload(Bool())
}


case class PC(node : CtrlLink) extends Area with throwPipe {
  import PC._
  
  val jumpcmd = Flow(JumpCmd())
  jumpcmd.setIdle()
  
  // val cacheReadCmd = Stream(L1FetchCmd())


  val pclogic = new node.Area {
    val pcReg = Reg(PCVal) init(0) simPublic()
    down(PCPLUS4).simPublic()
    down(PCVal).simPublic()
    up(PCVal) := pcReg
    PCPLUS4 := (pcReg + 4)
    up.valid := True

    VALIDCACHEREAD := False
    when(up.isFiring) {
      pcReg := jumpcmd.valid ? jumpcmd.payload.address | (PCVal + 4)
      VALIDCACHEREAD := True

    }
  }
}
