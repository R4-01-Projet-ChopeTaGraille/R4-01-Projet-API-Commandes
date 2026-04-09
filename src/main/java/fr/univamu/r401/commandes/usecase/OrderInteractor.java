package fr.univamu.r401.commandes.usecase;

import fr.univamu.r401.commandes.entity.Order;
import fr.univamu.r401.commandes.entity.OrderLine;
import fr.univamu.r401.commandes.usecase.exception.InvalidOrderException;
import fr.univamu.r401.commandes.usecase.exception.MenuNotFoundException;
import fr.univamu.r401.commandes.usecase.exception.OrderNotFoundException;
import fr.univamu.r401.commandes.usecase.exception.SubscriberNotFoundException;
import fr.univamu.r401.commandes.usecase.io.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Cas d'utilisation Commandes : contient toute la logique métier.
 * Implémente OrderInputBoundary et délègue la persistance et les appels externes via les gateways.
 */
@ApplicationScoped
public class OrderInteractor implements OrderInputBoundary {

    @Inject
    private OrderRepositoryGateway repository;

    @Inject
    private MenuGateway menuGateway;

    @Inject
    private SubscriberGateway subscriberGateway;

    @Override
    public OrderOutputData createOrder(CreateOrderInput input) {
        validate(input);

        subscriberGateway.findSubscriberById(input.subscriberId)
                .orElseThrow(() -> new SubscriberNotFoundException(input.subscriberId));

        Order order = new Order();
        order.setSubscriberId(input.subscriberId);
        order.setDeliveryAddress(input.deliveryAddress);
        order.setDeliveryDate(input.deliveryDate);
        order.setOrderDate(LocalDateTime.now());

        for (OrderLineInput lineInput : input.lines) {
            MenuSnapshot menu = menuGateway.findMenuById(lineInput.menuId)
                    .orElseThrow(() -> new MenuNotFoundException(lineInput.menuId));

            OrderLine line = new OrderLine();
            line.setMenuId(menu.id);
            line.setMenuName(menu.name);
            line.setUnitPrice(menu.totalPrice);
            line.setQuantity(lineInput.quantity);
            line.computeLinePrice();

            order.addLine(line);
        }

        order.computeTotalPrice();
        repository.save(order);

        return toOutputData(order);
    }

    @Override
    public OrderOutputData getOrderById(int id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return toOutputData(order);
    }

    @Override
    public List<OrderOutputData> listOrders(Integer subscriberId) {
        List<Order> orders = (subscriberId != null)
                ? repository.findBySubscriberId(subscriberId)
                : repository.findAll();

        List<OrderOutputData> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(toOutputData(order));
        }
        return result;
    }

    @Override
    public OrderOutputData updateOrder(int id, UpdateOrderInput input) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (input.deliveryDate != null && !input.deliveryDate.isAfter(LocalDate.now())) {
            throw new InvalidOrderException("La date de livraison doit être dans le futur");
        }

        if (input.deliveryAddress != null) order.setDeliveryAddress(input.deliveryAddress);
        if (input.deliveryDate != null)    order.setDeliveryDate(input.deliveryDate);

        repository.update(order);
        return toOutputData(order);
    }

    @Override
    public void cancelOrder(int id) {
        repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        repository.delete(id);
    }

    /** Valide les données d'entrée avant la création d'une commande. */
    private void validate(CreateOrderInput input) {
        if (input.deliveryDate == null || !input.deliveryDate.isAfter(LocalDate.now())) {
            throw new InvalidOrderException("La date de livraison doit être dans le futur");
        }
        if (input.lines == null || input.lines.isEmpty()) {
            throw new InvalidOrderException("La commande doit contenir au moins une ligne");
        }
        for (OrderLineInput line : input.lines) {
            if (line.quantity < 1) {
                throw new InvalidOrderException("La quantité doit être >= 1 (menuId=" + line.menuId + ")");
            }
        }
    }

    /** Convertit une entité Order en OrderOutputData. */
    private OrderOutputData toOutputData(Order order) {
        OrderOutputData out = new OrderOutputData();
        out.id = order.getId();
        out.subscriberId = order.getSubscriberId();
        out.orderDate = order.getOrderDate();
        out.deliveryAddress = order.getDeliveryAddress();
        out.deliveryDate = order.getDeliveryDate();
        out.totalPrice = order.getTotalPrice();

        List<OrderLineOutputData> lines = new ArrayList<>();
        for (OrderLine line : order.getLines()) {
            OrderLineOutputData lineOut = new OrderLineOutputData();
            lineOut.menuId = line.getMenuId();
            lineOut.menuName = line.getMenuName();
            lineOut.quantity = line.getQuantity();
            lineOut.unitPrice = line.getUnitPrice();
            lineOut.linePrice = line.getLinePrice();
            lines.add(lineOut);
        }
        out.lines = lines;

        return out;
    }
}
