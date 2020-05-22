package tqs.group4.bestofbooks.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static tqs.group4.bestofbooks.mocks.BuyerMock.buyer1;

import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

//import tqs.group4.bestofbooks.dto.RequestOrderDTO;
import org.mockito.internal.verification.VerificationModeFactory;
import tqs.group4.bestofbooks.dto.BookOrderDTO;
import tqs.group4.bestofbooks.dto.IncomingBookOrderDTO;
import tqs.group4.bestofbooks.dto.IncomingOrderDTO;
import tqs.group4.bestofbooks.dto.OrderDTO;
import tqs.group4.bestofbooks.exception.*;
import tqs.group4.bestofbooks.mocks.*;
import tqs.group4.bestofbooks.model.*;
import tqs.group4.bestofbooks.repository.CommissionRepository;
import tqs.group4.bestofbooks.repository.OrderRepository;
import tqs.group4.bestofbooks.repository.RevenueRepository;

public class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @Mock
    private RevenueService revenueService;

    @Mock
    private CommissionService commissionService;

    @InjectMocks
    private OrderService orderService;

    private int quantity = 2;
    private IncomingBookOrderDTO incomingBookOrderDTO1 = new IncomingBookOrderDTO(BookMocks.onTheRoad.getIsbn(),
            quantity);
    private IncomingBookOrderDTO incomingBookOrderDTO2 = new IncomingBookOrderDTO(BookMocks.onTheRoad.getIsbn(),
            quantity + 1);
    private List<IncomingBookOrderDTO> incomingBookOrderDTOList = new ArrayList(Arrays.asList(incomingBookOrderDTO1, incomingBookOrderDTO2));
    private IncomingOrderDTO incomingOrderDTO = new IncomingOrderDTO(incomingBookOrderDTOList, BuyerMock.buyer1.getUsername(),
            "XYZ", "Address");

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    public void compareListOrderDTO(List<OrderDTO> list1, List<OrderDTO> list2) {
        assertEquals(list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            compareOrderDTO(list1.get(i), list2.get(i));
        }
    }

    public void compareOrderDTO(OrderDTO thisOrder, OrderDTO thatOrder) {
        assertAll("comparingOrders",
                () -> assertEquals(thisOrder.getId(), thatOrder.getId()),
                () -> assertEquals(thisOrder.getAddress(), thatOrder.getAddress()),
                () -> assertEquals(thisOrder.getFinalPrice(), thatOrder.getFinalPrice()),
                () -> assertEquals(thisOrder.getPaymentReference(), thatOrder.getPaymentReference()),
                () -> compareListOfBookOrderDTO(thisOrder.getBookOrders(), thatOrder.getBookOrders())
        );
    }

    public void compareListOfBookOrderDTO(List<BookOrderDTO> list1, List<BookOrderDTO> list2) {
        assertEquals(list1.size(), list2.size());
        for (int i = 0; i < list1.size(); i++) {
            compareBookOrderDTO(list1.get(i), list2.get(i));
        }
    }

    public void compareBookOrderDTO(BookOrderDTO bookOrderDTO1, BookOrderDTO bookOrderDTO2) {
        assertAll(
                () -> assertEquals(bookOrderDTO1.getIsbn(), bookOrderDTO2.getIsbn()),
                () -> assertEquals(bookOrderDTO1.getAuthor(), bookOrderDTO2.getAuthor()),
                () -> assertEquals(bookOrderDTO1.getQuantity(), bookOrderDTO2.getQuantity()),
                () -> assertEquals(bookOrderDTO1.getTitle(), bookOrderDTO2.getTitle())
        );
    }

    @Test
    public void testGetOrderByKnownId() throws OrderNotFoundException {
        when(orderRepository.findById(anyInt())).thenReturn(OrderMocks.order1);
        compareOrderDTO(OrderDTO.fromOrder(OrderMocks.order1), orderService.getOrderById(3));
    }

    @Test
    public void testGetOrderByUnknownId_throwOrderNotFoundException() {
        when(orderRepository.findById(anyInt())).thenReturn(null);
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.getOrderById(3);
        });
    }

    @Test
    public void testGetOrderByKnownBuyerUsername() throws OrderNotFoundException {
        List<Order> orders = Lists.newArrayList(OrderMocks.order1);
        when(orderRepository.findByBuyerUsername(anyString())).thenReturn(orders);
        compareListOrderDTO(orders.stream().map(OrderDTO::fromOrder).collect(Collectors.toList()),
                orderService.getOrderByBuyerUsername("someBuyer"));
    }

    @Test
    public void testGetOrderByUnknownBuyerUsername_throwOrderNotFoundException() {
        when(orderRepository.findByBuyerUsername(anyString())).thenReturn(new ArrayList<>());

        assertThrows(OrderNotFoundException.class,
                () -> {
                    orderService.getOrderByBuyerUsername("someBuyer");
                }
        );
    }

    @Test
    public void whenTestOrderDTO_hasRepeatedPaymentReference_throwRepeatedPaymentReferenceException() {
        when(orderRepository.existsByPaymentReference("XYZ")).thenReturn(true);

        assertThrows(RepeatedPaymentReferenceException.class,
                () -> {
                    orderService.createOrderDTO(incomingOrderDTO);
                }
        );
    }

    @Test
    public void testCheckIfPaymentReferenceAlreadyExists() {
        when(orderRepository.existsByPaymentReference("XYZ")).thenReturn(true);
        when(orderRepository.existsByPaymentReference("ABC")).thenReturn(false);

        assertAll(
                () -> assertTrue(orderService.checkIfPaymentReferenceAlreadyExists("XYZ")),
                () -> assertFalse(orderService.checkIfPaymentReferenceAlreadyExists("ABC"))
        );
    }

    @Test
    void testCreateCommission() {
        Order order1 = new Order(
                "AC%EWRGER684654165",
                "77th st no 21, LA, CA, USA",
                10.00,
                buyer1
        );
        order1.setId(300);

        when(commissionService.computeCommissionAmountByOrder(order1)).thenReturn(2.00);
        Commission created = orderService.createCommission(order1);

        Commission fromOrder = new Commission(2.00, 300);

        assertAll(
                () -> assertEquals(fromOrder.getAmount(), created.getAmount()),
                () -> assertEquals(fromOrder.getOrderId(), created.getOrderId())
        );
    }

    @Test
    void testCreateRevenues() {
        when(revenueService.computeRevenueAmountByBookOrder(BookOrderMocks.bookOrder1)).thenReturn(100.00);

        List<Revenue> revenues = new ArrayList<>();
        Revenue revenue = new Revenue(100.00,
                BookOrderMocks.bookOrder1, BookOrderMocks.bookOrder1.getBook().getPublisherName());
        revenues.add(revenue);

        List<BookOrder> bookOrders = new ArrayList<>(Arrays.asList(BookOrderMocks.bookOrder1));
        List<Revenue> created = orderService.createRevenues(bookOrders);

        assertAll(
                () -> assertEquals(revenues.get(0).getPublisherName(), created.get(0).getPublisherName()),
                () -> assertEquals(revenues.get(0).getAmount(), created.get(0).getAmount()),
                () -> assertEquals(revenues.size(), created.size())
        );
    }

    @Test
    void testCreateOrder_WhenBooksAndBuyerAreKnown() throws BookNotFoundException, UserNotFoundException {
        when(bookService.computeFinalPriceFromIncomingOrder(incomingBookOrderDTOList)).thenReturn(100.00);
        when(userService.getBuyerFromUsername(BuyerMock.buyer1.getUsername())).thenReturn(BuyerMock.buyer1);

        Order expected = new Order("XYZ", "Address", 100.00, buyer1);
        Order created = orderService.createOrder(incomingOrderDTO);

        assertAll(
                () -> assertEquals(expected.getBuyer(), created.getBuyer()),
                () -> assertEquals(expected.getFinalPrice(), created.getFinalPrice()),
                () -> assertEquals(expected.getAddress(), created.getAddress()),
                () -> assertEquals(expected.getPaymentReference(), created.getPaymentReference())
        );
    }

    @Test
    public void testCreateOrder_WhenBuyerIsUnknown_throwUserNotFoundException() throws BookNotFoundException, UserNotFoundException {
        when(bookService.computeFinalPriceFromIncomingOrder(incomingBookOrderDTOList)).thenReturn(100.00);
        when(userService.getBuyerFromUsername(BuyerMock.buyer1.getUsername())).thenThrow(new UserNotFoundException());

        assertThrows(UserNotFoundException.class,
                () -> {
                    orderService.createOrder(incomingOrderDTO);
                }
        );
    }

    @Test
    public void testCreateOrder_WhenBookIsUnknown_throwBookNotFoundException() throws BookNotFoundException, UserNotFoundException {
        when(bookService.computeFinalPriceFromIncomingOrder(incomingBookOrderDTOList)).thenThrow(new BookNotFoundException());
        when(userService.getBuyerFromUsername(BuyerMock.buyer1.getUsername())).thenReturn(BuyerMock.buyer1);

        assertThrows(BookNotFoundException.class,
                () -> {
                    orderService.createOrder(incomingOrderDTO);
                }
        );
    }

    @Test
    public void testCreateOrderDTO_WhenPaymentReferenceIsRepeated_throwRepeatedPaymentReferenceException() {
        when(orderRepository.existsByPaymentReference("XYZ")).thenReturn(true);

        assertThrows(RepeatedPaymentReferenceException.class,
                () -> {
                    orderService.createOrderDTO(incomingOrderDTO);
                }
        );
    }

    @Test
    void testCreateOrderDTO_whenInputsAreValid() {
        OrderDTO expected = new OrderDTO(1, "XYZ", "Address", 100.00, buyer1.getUsername(), new ArrayList<>());
        Order order = new Order("XYZ", "Address", 100.00, buyer1);
        OrderDTO created = OrderDTO.fromOrder(order);

        assertAll(
                () -> assertEquals(expected.getBuyerName(), created.getBuyerName()),
                () -> assertEquals(expected.getFinalPrice(), created.getFinalPrice()),
                () -> assertEquals(expected.getAddress(), created.getAddress()),
                () -> assertEquals(expected.getPaymentReference(), created.getPaymentReference())
        );
    }

    @Test
    void testCreateBooksOrders_whenInputsAreValid() throws EmptyIncomingOrderException, NotEnoughStockException, BookNotFoundException {
        Order order = new Order("XYZ", "Address", BookMocks.onTheRoad.getPrice() * 5, buyer1);

        Map<Book, Integer> booksWithQuantities = new HashMap<>();
        booksWithQuantities.put(BookMocks.onTheRoad, 5);

        List<IncomingBookOrderDTO> incomingBookOrderDTOList2 = Lists.newArrayList(incomingBookOrderDTO1, incomingBookOrderDTO2);
        when(bookService.retrieveBooksAndQuantitiesFromIncomingOrderDTOS(incomingBookOrderDTOList2)).thenReturn(booksWithQuantities);

        List<BookOrder> created = orderService.createBooksOrders(order, incomingOrderDTO);

        assertAll(
                () -> assertEquals(1, created.size()),
                () -> assertEquals(5, created.get(0).getQuantity()),
                () -> assertEquals(BookMocks.onTheRoad, created.get(0).getBook()),
                () -> assertEquals(order, created.get(0).getOrder())
        );
    }

    @Test
    public void testCreateBooksOrders_WhenBookIsUnknown_throwBookNotFoundException() throws BookNotFoundException, NotEnoughStockException, EmptyIncomingOrderException {
        Order order = new Order("XYZ", "Address", 100.00, buyer1);
        when(bookService.retrieveBooksAndQuantitiesFromIncomingOrderDTOS(incomingBookOrderDTOList)).thenThrow(new BookNotFoundException());

        assertThrows(BookNotFoundException.class,
                () -> {
                    orderService.createBooksOrders(order, incomingOrderDTO);
                }
        );
    }

    @Test
    public void testCreateBooksOrders_WhenBookCopiesAreNotEnough_throwNotEnoughStockException() throws BookNotFoundException, NotEnoughStockException, EmptyIncomingOrderException {
        Order order = new Order("XYZ", "Address", 100.00, buyer1);
        when(bookService.retrieveBooksAndQuantitiesFromIncomingOrderDTOS(incomingBookOrderDTOList)).thenThrow(new NotEnoughStockException());

        assertThrows(NotEnoughStockException.class,
                () -> {
                    orderService.createBooksOrders(order, incomingOrderDTO);
                }
        );
    }

    @Test
    public void testCreateBooksOrders_WhenIncomingOrderIsEmpty_throwEmptyIncomingOrderException() throws BookNotFoundException, NotEnoughStockException, EmptyIncomingOrderException {
        Order order = new Order("XYZ", "Address", 100.00, buyer1);
        when(bookService.retrieveBooksAndQuantitiesFromIncomingOrderDTOS(incomingBookOrderDTOList)).thenThrow(new EmptyIncomingOrderException());

        assertThrows(EmptyIncomingOrderException.class,
                () -> {
                    orderService.createBooksOrders(order, incomingOrderDTO);
                }
        );
    }

    @Test
    public void testComputePriceForIncomingOrder_WhenBookIsUnknown_throwBookNotFoundException() throws BookNotFoundException {
        when(bookService.computeFinalPriceFromIncomingOrder(incomingBookOrderDTOList)).thenThrow(new BookNotFoundException());

        assertThrows(BookNotFoundException.class,
                () -> {
                    orderService.computePriceForIncomingOrder(incomingBookOrderDTOList);
                }
        );
    }

    @Test
    public void testComputePriceForIncomingOrder_WhenAllBooksAreKnown() throws BookNotFoundException {
        when(bookService.computeFinalPriceFromIncomingOrder(incomingBookOrderDTOList)).thenReturn(100.00);

        assertEquals(100.00, orderService.computePriceForIncomingOrder(incomingBookOrderDTOList));
    }
}
