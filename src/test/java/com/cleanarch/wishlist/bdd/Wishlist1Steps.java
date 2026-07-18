package com.cleanarch.wishlist.bdd;

import com.cleanarch.wishlist.application.config.WishlistPropertiesProvider;
import com.cleanarch.wishlist.application.dto.ProductIdsResponse;
import com.cleanarch.wishlist.application.usecase.WishlistUseCaseImpl;
import com.cleanarch.wishlist.domain.entity.Wishlist;
import com.cleanarch.wishlist.domain.repositorie.WishlistRepository;
import com.cleanarch.wishlist.domain.vo.ProductId;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class Wishlist1Steps {

    private final WishlistRepository wishlistRepository = mock(WishlistRepository.class);
    private final WishlistPropertiesProvider wishlistPropertiesProvider = mock(WishlistPropertiesProvider.class);
    private final WishlistUseCaseImpl wishlistUseCaseImpl =
            new WishlistUseCaseImpl(wishlistRepository, wishlistPropertiesProvider);
    private Set<ProductId> productIdSet = new HashSet<>();
    private String customerId;
    private String productId;

    @Given("wishlist is empty with id {string}")
    public void wishlist_is_empty(String id) {
        customerId = id;
        productIdSet.clear();
        when(wishlistRepository.findByCustomerId(customerId))
                .thenReturn(Optional.empty());
        assertTrue(wishlistUseCaseImpl.getAllProducts(customerId).getProductIds().isEmpty());
    }

    @When("the customer {string} adds product {string} to the wishlist")
    public void the_customer_adds_product_to_the_wishlist(String idCustomer, String idProduct) {
        customerId = idCustomer;
        productId = idProduct;

        Wishlist wishlist = new Wishlist("id", customerId, productIdSet);
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));
        when(wishlistPropertiesProvider.getMaxProducts()).thenReturn(5);

        wishlistUseCaseImpl.addProduct(customerId, productId);
        productIdSet.add(new ProductId(productId));
    }

    @Then("the wishlist of customer {string} should contain product {string}")
    public void the_wishlist_should_contain_product(String idCustomer, String idProduct) {
        customerId = idCustomer;
        productId = idProduct;

        Wishlist wishlist = new Wishlist("id", customerId, productIdSet);
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        ProductIdsResponse allProducts = wishlistUseCaseImpl.getAllProducts(customerId);
        assertTrue(allProducts.getProductIds().contains(idProduct));
    }

    @When("the customer {string} removes product {string} from the wishlist")
    public void the_customer_removes_product_from_the_wishlist(String idCustomer, String idProduct) {
        customerId = idCustomer;
        productId = idProduct;

        Wishlist wishlist = new Wishlist("id", customerId, productIdSet);
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));
        when(wishlistPropertiesProvider.getMaxProducts()).thenReturn(5);

        wishlistUseCaseImpl.removeProduct(customerId, productId);
        productIdSet.remove(new ProductId(productId));
    }

    @Then("the wishlist of customer {string} should not contain product {string}")
    public void then_wishlist_should_no_contain_product(String idCustomer, String idProduct) {
        customerId = idCustomer;
        productId = idProduct;

        Wishlist wishlist = new Wishlist("id", customerId, productIdSet);
        when(wishlistRepository.findByCustomerId(customerId)).thenReturn(Optional.of(wishlist));

        ProductIdsResponse allProducts = wishlistUseCaseImpl.getAllProducts(customerId);
        assertFalse(allProducts.getProductIds().contains(idProduct));
    }

}
