package fr.univamu.r401.commandes.usecase.io;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/** Données de sortie représentant une commande complète. */
public class OrderOutputData {

    public int id;
    public int subscriberId;
    public LocalDateTime orderDate;
    public String deliveryAddress;
    public LocalDate deliveryDate;
    public double totalPrice;
    public List<OrderLineOutputData> lines;

    public OrderOutputData() {}
}
