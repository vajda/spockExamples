package com.vajda.spockExamples.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.transaction.annotation.Transactional

import spock.lang.Specification


@SpringApplicationConfiguration(classes = Application.class)
class SpringSpec extends Specification {

    @Autowired
    ProductController productController
    
    @Autowired
    ProductRepository productRepository

	@Transactional
    def "product should exist in repo after save"() {
        expect:
        productController.findAll().empty

        when:
        productController.save(new ProductDTO("Laptop", 250, "New laptop model"))

        then:
        def products = productController.findAll()
        products.size() == 1
        products[0].name == "Laptop"
    }

	@Transactional
    def "product should not exist in repo after delete"() {
        given:
        def savedProduct = productController.save(new ProductDTO("Beer", 1, "Really good beer"))
        
        expect:
        !productController.findAll().empty

        when:
        productController.delete(savedProduct.id)

        then:
        productController.findAll().empty
    }

	@Transactional
    def "edit should update state of existing product"() {
        given:
        def savedProduct = productController.save(new ProductDTO("Beer", 1, "Really good beer"))

        when:
        productController.save(new ProductDTO(savedProduct.id, "Beer", 2, "Not so good"))
        
        then:
        def products = productController.findAll()
        products.size() == 1
        with(products[0]) {
            name == "Beer"
            price == 2
            description == "Not so good"
        }
    }

//    def cleanup() {
//        productRepository.deleteAll()
//    }
}
