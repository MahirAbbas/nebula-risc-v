package nebula.LsuL1


import spinal.core._
import spinal.lib._
import spinal.lib.misc.pipeline._



//Direct-mapped
// case class DCache() extends Area {

//   val addressWidth = 64 // 64 bits
//   val byteOffset = 2
//   val nSets = 256
//   val setIndexWidth = log2Up(nSets)
//   val setIndexRange = (setIndexWidth + 1 downto 2)
//   val tagWidth = addressWidth - setIndexWidth - byteOffset
//   val tagRange = 63 downto setIndexWidth + byteOffset
//   i

//   val l1bus = new ICacheBus() // Reused the same interface for simplicity
//   // val ramBus = new ramBus()

//   case class Tag() extends Bundle {
//     val valid = Bool()
//     val dirty = Bool()
//     val address = UInt(tagWidth bits)
//   }

//   val mem = Mem(Bits(32 bits), nSets) init(Seq.fill(nSets)(B"0"))
//   val tagBank = Mem(Tag(), nSets)

//   val read = new Area {
//     val index = l1bus.cmd.payload.address(setIndexRange)
//     val reqTag = l1bus.cmd.payload.address(tagRange)
//     val tag = tagBank.readAsync(index)

//     val hit = tag.valid && tag.address === reqTag

//     l1bus.rsp.setIdle()
//     ramBus.cmd.valid := False
//     ramBus.cmd.payload.address.assignDontCare()

//     when(l1bus.cmd.valid) {
//       when(hit) {
//         l1bus.rsp.valid := True
//         l1bus.rsp.payload.data := mem.readAsync(index)
//         l1bus.rsp.payload.miss := False
//       }.otherwise {
//         l1bus.rsp.payload.miss := True
//         l1bus.rsp.payload.data.assignDontCare()

//         ramBus.cmd.valid := True
//         ramBus.cmd.payload.address := l1bus.cmd.payload.address
//       }
//     }
//   }

//   val write = new Area {
//     val index = l1bus.cmd.payload.address(setIndexRange)
//     val reqTag = l1bus.cmd.payload.address(tagRange)
//     val tag = tagBank.readAsync(index)

//     val hit = tag.valid && tag.address === reqTag

//     l1bus.rsp.setIdle()
//     ramBus.cmd.valid := False
//     ramBus.cmd.payload.address.assignDontCare()

//     when(l1bus.cmd.valid) {
//       when(hit) {
//         l1bus.rsp.valid := True
//         // mem.write(index, l1bus.cmd.payload.data)
//         val updatedTag = tagBank.readSync(index)
//         updatedTag.dirty := True
//         tagBank.write(index, updatedTag)
//         l1bus.rsp.payload.miss := False
//       }.otherwise {
//         l1bus.rsp.payload.miss := True

//         // Prepare RAM fetch on miss
//         ramBus.cmd.valid := True
//         ramBus.cmd.payload.address := l1bus.cmd.payload.address
//       }
//     }
//   }

//   val refill = new Area {
//     val fetching = RegInit(False)
//     val index = Reg(UInt(setIndexWidth bits))
//     val reqTag = Reg(UInt(tagWidth bits))

//     val dirtyWriteback = RegInit(False)
//     val evictData = Reg(Bits(32 bits))
//     val evictAddr = Reg(UInt(addressWidth bits))

//     ramBus.rsp.ready := False

//     when(fetching) {
//       when(ramBus.rsp.valid) {
//         val tag = Tag()
//         tag.valid := True
//         tag.dirty := False
//         tag.address := reqTag

//         mem.write(index, ramBus.rsp.payload.data)
//         tagBank.write(index, tag)

//         fetching := False
//       }
//     }

//     when(ramBus.cmd.valid && !fetching) {
//       val tag = tagBank.readSync(index)

//       // Handle dirty line eviction
//       when(tag.valid && tag.dirty) {
//         dirtyWriteback := True
//         evictData := mem.readSync(index)
//         // evictAddr := tag.address ## index ## U"00" // Build full address for RAM write
//       }.otherwise {
//         dirtyWriteback := False
//       }

//       fetching := True
//       reqTag := l1bus.cmd.payload.address(tagRange)
//       index := l1bus.cmd.payload.address(setIndexRange)

//       when(dirtyWriteback) {
//         ramBus.cmd.valid := True
//         ramBus.cmd.payload.address := evictAddr
//         // ramBus.cmd.payload.data := evictData
//       }.otherwise {
//         ramBus.rsp.ready := True
//       }
//     }
//   }
// }
