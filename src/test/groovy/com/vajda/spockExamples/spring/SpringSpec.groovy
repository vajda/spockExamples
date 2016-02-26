package com.vajda.spockExamples.spring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.context.ApplicationContext

import spock.lang.Specification


@SpringApplicationConfiguration(classes = Application.class)
class SpringSpec extends Specification {

    @Autowired
    ApplicationContext context
    
    @Autowired
    ProductController productController
    
    @Autowired
    ProductRepository productRepository

    def "product should exist in repo after save"() {
        expect:
        productRepository.findAll().empty

        when:
        productController.save(new ProductDTO("Laptop", 250, "New laptop model"))

        then:
        def products = productRepository.findAll()
        products.size() == 1
        products[0].name == "Laptop"
    }

    def "product should not exist in repo after delete"() {
        given:
        def savedProduct = productRepository.save(new Product("Beer", 1, "Really tasty beer"))
        
        expect:
        !productRepository.findAll().empty

        when:
        productController.delete(savedProduct.id)

        then:
        productRepository.findAll().empty
    }

    def "edit should update state of existing product"() {
        given:
        def savedProduct = productRepository.save(new Product("Beer", 1, "Really tasty beer"))

        when:
        productController.save(new ProductDTO(savedProduct.id, "Beer", 2, "Not that tasty anymore"))
        
        then:
        def products = productRepository.findAll()
        products.size() == 1
        with(products[0]) {
            name == "Beer"
            price == 2
            description == "Not that tasty anymore"
        }
    }

//    def cleanup() {
//        productRepository.deleteAll()
//    }
}
