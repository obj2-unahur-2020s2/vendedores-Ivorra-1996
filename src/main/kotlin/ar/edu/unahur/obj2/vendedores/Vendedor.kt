package ar.edu.unahur.obj2.vendedores

import kotlin.reflect.jvm.internal.impl.serialization.deserialization.FlexibleTypeDeserializer

class Certificacion(val esDeProducto: Boolean, val puntaje: Int)

abstract class Vendedor {
  // Acá es obligatorio poner el tipo de la lista, porque como está vacía no lo puede inferir.
  // Además, a una MutableList se le pueden agregar elementos
  val certificaciones = mutableListOf<Certificacion>()

  // Definimos el método abstracto.
  // Como no vamos a implementarlo acá, es necesario explicitar qué devuelve.
  abstract fun puedeTrabajarEn(ciudad: Ciudad): Boolean

  // En las funciones declaradas con = no es necesario explicitar el tipo
  fun esVersatil() =
    certificaciones.size >= 3
      && this.certificacionesDeProducto() >= 1
      && this.otrasCertificaciones() >= 1

  // Si el tipo no está declarado y la función no devuelve nada, se asume Unit (es decir, vacío)
  fun agregarCertificacion(certificacion: Certificacion) {
    certificaciones.add(certificacion)
  }

  fun esFirme() = this.puntajeCertificaciones() >= 30

  fun certificacionesDeProducto() = certificaciones.count { it.esDeProducto }

  fun otrasCertificaciones() = certificaciones.count { !it.esDeProducto }

  fun puntajeCertificaciones() = certificaciones.sumBy { c -> c.puntaje }

  abstract fun influyente():Boolean
}

// En los parámetros, es obligatorio poner el tipo
abstract class VendedorFijo(val ciudadOrigen: Ciudad) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad) = ciudad == ciudadOrigen
}

// A este tipo de List no se le pueden agregar elementos una vez definida
class Viajante(val provinciasHabilitadas: List<Provincia>) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return provinciasHabilitadas.contains(ciudad.provincia)
  }
  override fun influyente() = provinciasHabilitadas.sumBy { s -> s.poblacion } >= 10000000
}

class ComercioCorresponsal(val ciudades: List<Ciudad>) : Vendedor() {
  override fun puedeTrabajarEn(ciudad: Ciudad): Boolean {
    return ciudades.contains(ciudad)
  }
  override fun influyente() = ciudades.size >= 5  || ciudades.map { c -> c.provincia }.toSet().size >= 3
}

class CentrosDeDistribucion(val ciudad : Ciudad){
  val vendedores = mutableListOf<Vendedor>()

  fun agregarVendedor(vendedor: Vendedor){
    if(!vendedores.contains(vendedor)){
      vendedores.add(vendedor)
    }
    else{
      error("Error esta en la lista")
    }
  }
  //el vendedor estrella, que es el que tiene mayor puntaje total por certificaciones.
  fun vendedorEstrella() = vendedores.maxBy { v -> v.puntajeCertificaciones()}

  //si puede cubrir, o no, una ciudad dada. La condición es que al menos uno de los vendedores registrados pueda trabajar en esa ciudad.
  fun puedeCubrir(ciudad: Ciudad) = vendedores.any { it.puedeTrabajarEn(ciudad)}

  //la colección de vendedores genéricos registrados. Un vendedor se considera genérico si tiene al menos una certificación que no es de productos.
  fun vendedoresGenericoRegistrado() = vendedores.any { it.otrasCertificaciones()  >= 1 }

  //si es robusto, la condición es que al menos 3 de sus vendedores registrados sea firme.
  fun esRobusto() = vendedores.filter { it.esFirme() == true }.toSet().size >= 3
}

