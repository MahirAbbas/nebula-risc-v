package nebula.LsuL1


import spinal.core._
import spinal.lib._

case class RamFetchCmd() extends Bundle {
  val address = UInt(64 bits)
}
case class RamFetchRsp() extends Bundle {
  val address = UInt(64 bits)
  val data = Bits(32 bits)
}
case class L1FetchCmd() extends Bundle {
  val address = UInt(64 bits)
}
case class L1FetchRsp() extends Bundle {
  val miss = Bool()
  val data = Bits(32 bits)
}

case class L1StoreCmd() extends Bundle {
  val address = UInt(64 bits)
  val data    = Bits(64 bits)
}

case class L1StoreRsp() extends Bundle {
  val error = Bool()
}

case class RamStoreCmd() extends Bundle {
  val address = UInt(64 bits)
  val data    = Bits(64 bits)
}
case class RamStoreRsp() extends Bundle {
  val validStore = Bool()
}

// case class DCacheBus() extends Bundle with IMasterSlave {
  
// }

case class RamBus() extends Bundle with IMasterSlave {
  val ramFetchCmd = Stream(RamFetchCmd())
  val ramFetchRsp = Stream(RamFetchRsp())
  
  val ramStoreCmd = Stream(RamStoreCmd())
  val ramStoreRsp = Stream(RamStoreRsp())
  
  override def asMaster() = {
    master(ramFetchCmd, ramStoreCmd)
    slave(ramFetchRsp, ramStoreRsp)
  }

}