package com.vajda.spockExamples.spring;

import java.util.List;

import org.jtransfo.JTransfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("products")
public class ProductController {

    private final JTransfo jTransfo;
    private final ProductRepository productRepository;
    
    @Autowired
    public ProductController(JTransfo jTransfo, ProductRepository productRepository) {
        this.jTransfo = jTransfo;
        this.productRepository = productRepository;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<ProductDTO> list() {
        List<Product> products = productRepository.findAll();
        return jTransfo.convertList(products, ProductDTO.class);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ProductDTO save(ProductDTO product) {
        return saveEdit(product);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ProductDTO edit(ProductDTO product) {
        return saveEdit(product);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (id < 1) {
            return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
        if (productRepository.exists(id)) {
            productRepository.delete(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    private ProductDTO saveEdit(ProductDTO product) {
        Product productToSave = jTransfo.convertTo(product, Product.class);
        Product editedProduct = productRepository.save(productToSave);
        return jTransfo.convertTo(editedProduct, ProductDTO.class);
    }
}
