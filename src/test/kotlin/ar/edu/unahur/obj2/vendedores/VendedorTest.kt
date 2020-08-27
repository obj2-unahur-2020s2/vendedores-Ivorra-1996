package ar.edu.unahur.obj2.vendedores

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull

class VendedorTest : DescribeSpec({
  val misiones = Provincia(1300000)
  val sanIgnacio = Ciudad(misiones)
  val viajante = Viajante(listOf(misiones))
  val corresponsal = ComercioCorresponsal(listOf(sanIgnacio))

  describe("Vendedor fijo") {
    val obera = Ciudad(misiones)
    val vendedorFijo = VendedorFijo(obera)

    describe("puedeTrabajarEn") {
      it("su ciudad de origen") {
        vendedorFijo.puedeTrabajarEn(obera).shouldBeTrue()
      }
      it("otra ciudad") {
        vendedorFijo.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }
  }

  describe("Viajante") {
    val cordoba = Provincia(2000000)
    val villaDolores = Ciudad(cordoba)
    val viajante = Viajante(listOf(misiones))

    describe("puedeTrabajarEn") {
      it("una ciudad que pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(sanIgnacio).shouldBeTrue()
      }
      it("una ciudad que no pertenece a una provincia habilitada") {
        viajante.puedeTrabajarEn(villaDolores).shouldBeFalse()
      }
    }
  }

  describe("Corresponsal1") {
    val buenosAires = Provincia(6000000)
    val moron = Ciudad(buenosAires)
    val corresponsal = ComercioCorresponsal(listOf(moron))

    describe("puedeTrabajarEn") {
      it("una provincia habilitada") {
        corresponsal.puedeTrabajarEn(moron).shouldBeTrue()
      }
      it("una  que no pertenece a  provincia habilitada") {
        corresponsal.puedeTrabajarEn(sanIgnacio).shouldBeFalse()
      }
    }

    describe("Es influyente") {
      it("nop") {
        corresponsal.influyente().shouldBeFalse()
      }

    }
  }

  describe("Corresponsal2") {
    val buenosAires = Provincia(6000000)
    val cordoba = Provincia(6000000)
    val sanluis = Provincia(6000000)
    val santafe = Provincia(6000000)
    val misiones = Provincia(6000000)

    val posadas = Ciudad(misiones)
    val santafee = Ciudad(santafe)
    val merlo = Ciudad(sanluis)
    val rioCuarto = Ciudad(cordoba)
    val moron = Ciudad(buenosAires)

    val corresponsales = ComercioCorresponsal(listOf(moron,rioCuarto,merlo,santafee,posadas))

    describe("ciudades"){
      it("puede trabajar"){
        corresponsales.puedeTrabajarEn(posadas).shouldBeTrue()
        corresponsales.puedeTrabajarEn(santafee).shouldBeTrue()
        corresponsales.puedeTrabajarEn(merlo).shouldBeTrue()
        corresponsales.puedeTrabajarEn(rioCuarto).shouldBeTrue()
        corresponsales.puedeTrabajarEn(moron).shouldBeTrue()
      }
    }
    describe("Es influyente") {
    it("sep") {
      corresponsales.influyente().shouldBeTrue()
    }
    }
  }
  describe("CentrosDeDistribucion") {
    val buenosAires = Provincia(6000000)
    val castelar = Ciudad(buenosAires)
    val centros = CentrosDeDistribucion(castelar)

    describe("agregar_vendedor_a_la_lista") {
      it("vendedor_agregado") {
        centros.agregarVendedor(viajante)
        centros.agregarVendedor(corresponsal)
      }
      shouldThrowAny {
                centros.agregarVendedor(viajante)
            }
       }
  }

  })




