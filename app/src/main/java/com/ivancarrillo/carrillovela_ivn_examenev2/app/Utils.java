package com.ivancarrillo.carrillovela_ivn_examenev2.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Utils {
    public static List<Store> getSampleData() {

        List<Store> stores = new ArrayList<>();

        // ================= STORE 1 =================
        Store store1 = new Store(
                "Supermercado Central",
                "Calle Mayor 12",
                40.4168,
                -3.7038,
                true
        );

        store1.getItems().add(new Item("Leche entera", "Alimentación", 1.10, 0, false));
        store1.getItems().add(new Item("Pan de molde", "Alimentación", 1.60, 0, false));
        store1.getItems().add(new Item("Huevos (docena)", "Alimentación", 2.80, 0, false));
        store1.getItems().add(new Item("Arroz", "Alimentación", 1.20, 0, false));
        store1.getItems().add(new Item("Pasta", "Alimentación", 1.00, 0, false));

        store1.getItems().add(new Item("Agua mineral 1.5L", "Bebidas", 0.50, 0, false));
        store1.getItems().add(new Item("Refresco cola", "Bebidas", 1.40, 0, false));
        store1.getItems().add(new Item("Zumo de naranja", "Bebidas", 1.80, 0, false));
        store1.getItems().add(new Item("Cerveza lata", "Bebidas", 0.90, 0, false));

        store1.getItems().add(new Item("Detergente lavadora", "Limpieza", 4.50, 0, false));
        store1.getItems().add(new Item("Lavavajillas", "Limpieza", 2.10, 0, false));
        store1.getItems().add(new Item("Lejía", "Limpieza", 1.30, 0, false));
        store1.getItems().add(new Item("Limpiacristales", "Limpieza", 1.90, 0, false));

        store1.getItems().add(new Item("Papel higiénico (6)", "Hogar", 3.20, 0, false));
        store1.getItems().add(new Item("Servilletas", "Hogar", 1.10, 0, false));
        store1.getItems().add(new Item("Papel de cocina", "Hogar", 2.00, 0, false));
        store1.getItems().add(new Item("Bolsas de basura", "Hogar", 1.70, 0, false));

        store1.getItems().add(new Item("Chocolate negro", "Otros", 1.50, 0, false));
        store1.getItems().add(new Item("Patatas fritas", "Otros", 1.40, 0, false));

        stores.add(store1);

        // ================= STORE 2 =================
        Store store2 = new Store(
                "HiperMarket Norte",
                "Avenida del Norte 45",
                41.3874,
                2.1686,
                false
        );

        store2.getItems().add(new Item("Leche semidesnatada", "Alimentación", 1.05, 0, false));
        store2.getItems().add(new Item("Pan integral", "Alimentación", 1.80, 0, false));
        store2.getItems().add(new Item("Filetes de pollo", "Alimentación", 5.20, 0, false));
        store2.getItems().add(new Item("Carne picada", "Alimentación", 4.60, 0, false));
        store2.getItems().add(new Item("Pescado congelado", "Alimentación", 6.00, 0, false));

        store2.getItems().add(new Item("Agua con gas", "Bebidas", 0.60, 0, false));
        store2.getItems().add(new Item("Refresco naranja", "Bebidas", 1.35, 0, false));
        store2.getItems().add(new Item("Té frío", "Bebidas", 1.50, 0, false));

        store2.getItems().add(new Item("Suavizante", "Limpieza", 3.80, 0, false));
        store2.getItems().add(new Item("Fregasuelos", "Limpieza", 1.95, 0, false));
        store2.getItems().add(new Item("Quitagrasas", "Limpieza", 2.30, 0, false));

        store2.getItems().add(new Item("Papel aluminio", "Hogar", 1.60, 0, false));
        store2.getItems().add(new Item("Film transparente", "Hogar", 1.50, 0, false));
        store2.getItems().add(new Item("Velas", "Hogar", 2.20, 0, false));

        store2.getItems().add(new Item("Galletas", "Otros", 1.70, 0, false));
        store2.getItems().add(new Item("Café molido", "Otros", 3.90, 0, false));
        store2.getItems().add(new Item("Azúcar", "Otros", 1.20, 0, false));
        store2.getItems().add(new Item("Sal fina", "Otros", 0.70, 0, false));
        store2.getItems().add(new Item("Aceite de oliva", "Otros", 6.50, 0, false));

        stores.add(store2);

        // ================= STORE 3 =================
        Store store3 = new Store(
                "EcoShop Barrio",
                "Plaza Verde 3",
                37.3891,
                -5.9845,
                false
        );

        store3.getItems().add(new Item("Leche ecológica", "Alimentación", 1.60, 0, false));
        store3.getItems().add(new Item("Pan artesanal", "Alimentación", 2.20, 0, false));
        store3.getItems().add(new Item("Huevos camperos", "Alimentación", 3.50, 0, false));
        store3.getItems().add(new Item("Verduras variadas", "Alimentación", 4.00, 0, false));
        store3.getItems().add(new Item("Fruta de temporada", "Alimentación", 3.80, 0, false));

        store3.getItems().add(new Item("Zumo ecológico", "Bebidas", 2.40, 0, false));
        store3.getItems().add(new Item("Bebida vegetal", "Bebidas", 2.10, 0, false));

        store3.getItems().add(new Item("Detergente ecológico", "Limpieza", 5.60, 0, false));
        store3.getItems().add(new Item("Lavavajillas ecológico", "Limpieza", 3.90, 0, false));

        store3.getItems().add(new Item("Bolsas reutilizables", "Hogar", 2.50, 0, false));
        store3.getItems().add(new Item("Papel reciclado", "Hogar", 3.00, 0, false));
        store3.getItems().add(new Item("Estropajo vegetal", "Hogar", 1.80, 0, false));

        store3.getItems().add(new Item("Miel", "Otros", 4.20, 0, false));
        store3.getItems().add(new Item("Infusión", "Otros", 2.60, 0, false));
        store3.getItems().add(new Item("Chocolate bio", "Otros", 2.30, 0, false));
        store3.getItems().add(new Item("Cereales integrales", "Otros", 3.10, 0, false));
        store3.getItems().add(new Item("Legumbres secas", "Otros", 2.00, 0, false));
        store3.getItems().add(new Item("Semillas", "Otros", 1.90, 0, false));
        store3.getItems().add(new Item("Harina integral", "Otros", 1.70, 0, false));

        stores.add(store3);

        return stores;
    }

    public static String buildShoppingListEmailBody(Store activeStore, List<Item> itemsWithQty) {

        StringBuilder sb = new StringBuilder();
        sb.append("Lista de la compra - ").append(activeStore.getName()).append("\n\n");

        double total = 0.0;

        for (int i = 0; i < itemsWithQty.size(); i++) {
            Item it = itemsWithQty.get(i);

            double subtotal = it.getPrice() * it.getQuantity();
            total += subtotal;

            sb.append("- ")
                    .append(it.getName())
                    .append(" x").append(it.getQuantity())
                    .append(" (")
                    .append(String.format(Locale.getDefault(), "%.2f", subtotal))
                    .append(" €)")
                    .append("\n");
        }

        sb.append("\nTotal: ")
                .append(String.format(Locale.getDefault(), "%.2f €", total));

        return sb.toString();
    }

    static public String openStoreInMaps(Store store) {

        String uriString;

        // Si hay coordenadas, usamos geo:lat,lon?q=lat,lon(nombre)
        if (store.getLat() != null && store.getLon() != null) {
            String label = store.getName() != null ? store.getName() : "Tienda";
            uriString = "geo:" + store.getLat() + "," + store.getLon()
                    + "?q=" + store.getLat() + "," + store.getLon()
                    + "(" + android.net.Uri.encode(label) + ")";
        } else {
            // Si no hay coords, buscamos por dirección o por nombre
            String query = store.getAddress() != null ? store.getAddress() : store.getName();
            if (query == null) query = "Tienda";

            uriString = "geo:0,0?q=" + android.net.Uri.encode(query);
        }

        return uriString;

    }

}
