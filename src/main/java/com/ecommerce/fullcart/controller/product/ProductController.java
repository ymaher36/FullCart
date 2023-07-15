package com.ecommerce.fullcart.controller.product;

import com.ecommerce.fullcart.dto.ProductDTO;
import com.ecommerce.fullcart.entity.product.Category;
import com.ecommerce.fullcart.entity.product.Inventory;
import com.ecommerce.fullcart.entity.product.Price;
import com.ecommerce.fullcart.entity.product.Product;
import com.ecommerce.fullcart.entity.user.User;
import com.ecommerce.fullcart.service.FilesStorageService;
import com.ecommerce.fullcart.service.product.CategoryService;
import com.ecommerce.fullcart.service.product.InventoryService;
import com.ecommerce.fullcart.service.product.PriceService;
import com.ecommerce.fullcart.service.product.ProductService;
import com.ecommerce.fullcart.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    private CategoryService categoryService;
    private PriceService priceService;
    private InventoryService inventoryService;
    private UserService userService;

    private FilesStorageService filesService;
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/uploads/products";

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, UserService userService,
                             PriceService priceService, InventoryService inventoryService, FilesStorageService filesService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.priceService = priceService;
        this.inventoryService = inventoryService;
        this.filesService = filesService;
    }


    @GetMapping("/my-products")
    @PreAuthorize("hasAuthority('Seller')")
    public String myProducts(Model theModel, HttpServletRequest request) throws Exception {
        Map<Integer, String> categoriesMap = new HashMap<>();
        ProductDTO productDTO = new ProductDTO();
        List<ProductDTO> myProducts = new ArrayList<>();

        int userId = (int) request.getSession().getAttribute("userId");
        User user = userService.findUserById(userId);

        for (Category category : categoryService.findAll()) {
            categoriesMap.put(category.getId(), category.getName());
        }
        for (Product product : productService.findUserlatestProducts(user.getId())) {
            Price price = priceService.findLatestPriceOfProduct(product.getId());
            Inventory inventory = inventoryService.findProductInventory(product.getId());
            ProductDTO temp = new ProductDTO(
                    product.getId(), product.getName(), product.getDescription(),
                    product.getImage(), product.getCategory(),
                    price.getSellingPrice(),
                    (inventory.getAvaQuantity() - inventory.getSoldQuantity()));
            myProducts.add(temp);
        }
        theModel.addAttribute("newProduct", productDTO);
        theModel.addAttribute("myProducts", myProducts);
        theModel.addAttribute("categoriesMap", categoriesMap);
        theModel.addAttribute("user", user);
        return "seller/my-products";
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('Seller')")
    public String addProduct(@Valid @ModelAttribute("newProduct") ProductDTO productDTO,
                             BindingResult bindingResult,
                             @RequestParam(name = "newProductCategory") int categoryId,
                             HttpServletRequest request,
                             @RequestParam("newProductImage") MultipartFile file,
                             Model theModel) throws Exception {
        /*ToDo: Handle backend validation and sending it to front-end,
            make sure only images are uploaded,
             price, inventory and edit*/

        // form validation
        if (bindingResult.hasErrors()) {
            System.out.println(productDTO.getImage());
            int userId = (int) request.getSession().getAttribute("userId");
            User user = userService.findUserById(userId);
            Map<Integer, String> categoriesMap = new HashMap<>();
            List<ProductDTO> myProducts = new ArrayList<>();

            for (Category category : categoryService.findAll()) {
                categoriesMap.put(category.getId(), category.getName());
            }
            for (Product product : productService.findUserlatestProducts(user.getId())) {
                Price price = priceService.findLatestPriceOfProduct(product.getId());
                Inventory inventory = inventoryService.findProductInventory(product.getId());
                ProductDTO temp = new ProductDTO(
                        product.getId(), product.getName(), product.getDescription(),
                        product.getImage(), product.getCategory(),
                        price.getSellingPrice(),
                        (inventory.getAvaQuantity() - inventory.getSoldQuantity()));
                myProducts.add(temp);
            }
            theModel.addAttribute("myProducts", myProducts);
            theModel.addAttribute("categoriesMap", categoriesMap);
            theModel.addAttribute("user", user);
            theModel.addAttribute("showModal", true);
            return "seller/my-products";
        }
        if (!file.isEmpty()) {
            filesService.save(file, UPLOAD_DIRECTORY);
            productDTO.setImage(file.getOriginalFilename());
        }
        int sellerId = (int) request.getSession().getAttribute("userId");
        productDTO.setCategory(categoryService.findById(categoryId));
        productDTO.setSeller(userService.findUserById(sellerId));
        Product product = productService.save(productDTO);
        Price price = priceService.save(product, productDTO.getPrice());
        Inventory inventory = inventoryService.create(product, productDTO.getQuantity());
        return "redirect:/product/my-products";
    }

    @GetMapping(value = "/img", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImageWithMediaType(@RequestParam(name = "img") String imgFileName) throws IOException {
        return filesService.load(UPLOAD_DIRECTORY, imgFileName).getContentAsByteArray();
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('Seller')")
    public String deleteProduct(@PathVariable("id") int productId,
                                RedirectAttributes redirectAttributes,
                                HttpServletRequest request) {
        int sellerId = (int) request.getSession().getAttribute("userId");
        productService.deleteByIdAndSellerId(productId, sellerId);
        return "redirect:/product/my-products";
    }

    @PostMapping("/changePrice")
    @PreAuthorize("hasAuthority('Seller')")
    public String changePrice(@RequestParam("pricingProductId") int productId,
                              @RequestParam("pricingPrice") double price,
                              HttpServletRequest request) {

        int sellerId = (int) request.getSession().getAttribute("userId");
        Product product = productService.findByIdAndSellerId(productId, sellerId);
        priceService.save(product, price);

        return "redirect:/product/my-products";

    }

    @PostMapping("/addQuantity")
    @PreAuthorize("hasAuthority('Seller')")
    public String addQuantity(@RequestParam("addQuantityProductId") int productId,
                              @RequestParam("quantity") int quantity,
                              HttpServletRequest request) {
        int sellerId = (int) request.getSession().getAttribute("userId");
        Product product = productService.findByIdAndSellerId(productId, sellerId);
        quantity = quantity + product.getInventory().getAvaQuantity();
        inventoryService.save(product.getId(), quantity);
        return "redirect:/product/my-products";

    }

    @GetMapping("/{id}")
    public String productPage(@PathVariable int id,
                              Model theModel,
                              HttpServletRequest request) throws Exception {
        Product product = productService.findById(id);
        Price price = priceService.findLatestPriceOfProduct(id);
        Inventory inventory = inventoryService.findProductInventory(id);
        ProductDTO productDTO = new ProductDTO(
                id, product.getName(), product.getDescription(), product.getImage(),
                product.getCategory(), price.getSellingPrice(),
                (inventory.getAvaQuantity() - inventory.getSoldQuantity())
        );
        if (request.getSession().getAttribute("userId") != null) {
            int userId = (int) request.getSession().getAttribute("userId");
            User user = userService.findUserById(userId);
            theModel.addAttribute("user", user);
        }
        theModel.addAttribute("product", productDTO);
        return "product-details";
    }

    @GetMapping("/search")
    @ResponseBody

    public Map<Integer, String> productSearch(@RequestParam(name = "search") String search) {
        Map<Integer, String> productMap = new HashMap<>();
        for (Product product : productService.findProductsByName(search)) {
            productMap.put(product.getId(), product.getName());
        }
        return productMap;
    }
}
