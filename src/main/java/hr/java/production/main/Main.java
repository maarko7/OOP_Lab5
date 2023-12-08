package hr.java.production.main;

import hr.java.production.enums.City;
import hr.java.production.exception.DuplicateCategorySelectionException;
import hr.java.production.exception.DuplicateItemCategoryException;
import hr.java.production.exception.DuplicateItemSelectionException;
import hr.java.production.genericsi.FoodStore;
import hr.java.production.genericsi.TechnicalStore;
import hr.java.production.model.*;
import hr.java.production.sort.ProductionSorter;
import hr.java.production.sort.VolumeComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;


//dodati slucaj sa vise istih cijena / istih volumena
//sto ako se unese nesto osim broja u dimenzije?? nismo jos radili?
//ubaciti final vrijenosti za broj trgovina i ostalog?
//u svakom ponovnom upisu ispisati ponudu

//U kodu je pretpostavka da je prva kategorija [0] hrana.
//Promijeniti izbor iz integera pa ponuditi korisniku odabir

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        FoodStore foodStore = new FoodStore<>("Trgovina hranom", "www.foodstore.hr",
                new HashSet<>(), new ArrayList<>());
        TechnicalStore technicalStore = new TechnicalStore<>("Trgovina tehnikom", "www.technicalstore.hr",
                new HashSet<>(), new ArrayList<>());

        Category[] categories = enterCategory(scanner);
        List<Item> items = enterItems(scanner, categories, foodStore, technicalStore);
        Factory[] factories = enterFactory(scanner, items);
        Store[] stores = enterStore(scanner, items);

        checkingArticleVolume(factories);
        checkingArticlePrice(stores);
        checkingArticleKilocaloriesAndPrice(items);
        checkingLaptopPrice(items);
        checkingShortestWarrantyLaptop(items);
        itemsByCategory(items);

        Instant startWithLambda = Instant.now();
        sortItemsByVolume(items);
        calculateAverageVolume(items);
        calculateAveragePriceForAboveAverageVolume(items);
        aboveAverageNumberOfItems(stores);
        Instant endWithLambda = Instant.now();

        Duration durationWithLambda = Duration.between(startWithLambda, endWithLambda);

        Instant startWithoutLambda = Instant.now();
        sortItemsByVolumeWithoutLambda(items);
        calculateAverageVolumeWithoutLambda(items);
        calculateAveragePriceForAboveAverageVolumeWithoutLambda(items);
        aboveAverageNumberOfItemsWithoutLambda(stores);
        Instant endWithoutLambda = Instant.now();

        Duration durationWithoutLambda = Duration.between(startWithoutLambda, endWithoutLambda);

        System.out.println("Vrijeme izvođenja sa lambdama: " + durationWithLambda.toMillis() + " ms");
        System.out.println("Vrijeme izvođenja bez lambdi: " + durationWithoutLambda.toMillis() + " ms");

        System.out.println("=====================");

        printNumberOfItems(stores);

//        printNumberOfItems(stores);
//        foodStore.getFoodItems().forEach(System.out::println);
    }

    /**
     * Metoda za unos podataka o trgovinama.
     *
     * @param scanner Ulazna referenca za unos podataka.
     * @param items   Polje dostupnih proizvoda.
     * @return Polje trgovina unesenih od strane korisnika.
     */
    public static Store[] enterStore(Scanner scanner, List<Item> items) {
        Set<Item> itemsSet = new HashSet<>(items);
        Store[] stores = new Store[2];
        Integer chosenItemIndex = -1;
        Integer numberOfItems = -1;
        Set<Item> chosenItems = new HashSet<>();


        System.out.println("Unesite dvije trgovine.");
        for (int i = 0; i < stores.length; i++) {
            System.out.println("Ime " + (i + 1) + ". trgovine: ");
            String name = scanner.nextLine();

            System.out.println("Web adresa " + (i + 1) + ". trgovine: ");
            String webAdress = scanner.nextLine();


            System.out.println("Proizvodi u ponudi: ");
            int index = 1;
            for (Item item : itemsSet) {
                System.out.println(index + ". " + item.getName());
                index++;
            }

            Boolean errorInput = false;
            do {
                errorInput = false;
                try {
//                    do {
                    System.out.println("Koliko proizvoda želite dodati?");
                    numberOfItems = scanner.nextInt();
                    scanner.nextLine();
//                    } while (numberOfItems < 1 || numberOfItems > 5);
                } catch (InputMismatchException e) {
                    errorInput = true;
                    scanner.nextLine();
                    logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                    System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                }
            } while (errorInput || numberOfItems < 1 || numberOfItems > itemsSet.size());

//            Item[] chosenItems = new Item[numberOfItems];

//            Item newItem = null;
//            do {
            for (int k = 0; k < numberOfItems; k++) {
                do {
                    errorInput = false;
                    try {
                        do {
                            System.out.println("Odaberite željeni proizvod: ");
                            chosenItemIndex = scanner.nextInt();
                            scanner.nextLine();
                        } while (chosenItemIndex < 1 || chosenItemIndex > itemsSet.size());
                        Item newItem = itemsSet.stream()
                                .skip(chosenItemIndex - 1)
                                .findFirst()
                                .orElseThrow();

                        chosenItems.add(newItem);
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    } catch (NoSuchElementException e) {
                        errorInput = true;
                        logger.error("Proizvod nije pronađen.", e);
                        System.out.println("Proizvod nije pronađen. Pokušajte ponovno.");
                    }
//                        catch (DuplicateItemSelectionException e) {
//                            errorInput = true;
//                            scanner.nextLine();
//                            logger.error("Korisnik je unio artikl koji je već odabran u tvornici.", e);
//                            System.out.println("Proizvod je već odabran u tvornici. Molimo pokušajte ponovno.");
//                        }
                } while (errorInput);

//                    do {
//                        try {
//                            System.out.println("Odaberite željeni proizvod: ");
//                            chosenItemIndex = scanner.nextInt();
//                            scanner.nextLine();
//                        } catch (InputMismatchException e) {
//                            errorInput = true;
//                            scanner.nextLine();
//                            logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
//                            System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
//                        }
//                    } while (errorInput);
//
//                    Item newItem = items[chosenItemIndex - 1];
            }
//            } while (chosenItemIndex < 0 || chosenItemIndex > items.length);
            Store newStore = new Store(name, webAdress, chosenItems);
            stores[i] = newStore;
        }
        return stores;
    }

    /**
     * Metoda za unos podataka o tvornicama.
     *
     * @param scanner Ulazna referenca za unos podataka.
     * @param items   Polje dostupnih proizvoda.
     * @return Polje tvornica unesenih od strane korisnika.
     */
    public static Factory[] enterFactory(Scanner scanner, List<Item> items) {
        Set<Item> itemsSet = new HashSet<>(items);
        Factory[] factories = new Factory[2];
        Integer chosenItemIndex = -1;
        Integer numberOfItems = -1;
        Integer chosenCityIndex = -1;
        City chosenCity = null;
        Set<Item> chosenItems = new HashSet<>();
        List<Item> tempList = new ArrayList<>(chosenItems);



        /*
        kreiranje tvornice
         */
        System.out.println("Unesite dvije tvornice.");
        for (int i = 0; i < factories.length; i++) {
            System.out.println("Ime " + (i + 1) + ". tvornice: ");
            String name = Optional.ofNullable(scanner.nextLine()).orElse("TVORNICA");


            /*
            adresiranje tvornice
             */
            //Nova metoda?
            System.out.println("Adresa tvornice: ");
            System.out.println("U kojem se gradu nalazi tvornica?");
            Boolean errorInput = false;
            do {
                int j = 1;
                for (City city : City.values()) {
                    System.out.println(j + ". " + city);
                    j++;
                }
                System.out.println("Odaberite unosom rednog broja: ");
                errorInput = false;
                try {
                    chosenCityIndex = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    errorInput = true;
                    scanner.nextLine();
                    logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                    System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                }
                if (chosenCityIndex < 1 || chosenCityIndex > City.values().length) {
                    System.out.println("Unijeli ste neispavan broj. Pokušajte ponovno.");
                }
            } while (errorInput || chosenCityIndex < 1 || chosenCityIndex > 4);
            switch (chosenCityIndex) {
                case 1:
                    chosenCity = City.ZAGREB;
                    break;
                case 2:
                    chosenCity = City.RIJEKA;
                    break;
                case 3:
                    chosenCity = City.SPLIT;
                    break;
                case 4:
                    chosenCity = City.OSIJEK;
                    break;
                default:
                    System.out.println("Neispravan odabir.");
                    break;
            }
            System.out.println("Ime ulice: ");
            String street = scanner.nextLine();
            System.out.println("Kućni broj: ");
            String houseNumber = scanner.nextLine();

            Address address = new Address(street, houseNumber, chosenCity);



            /*
            odabir proizvoda
             */
            System.out.println("Proizvodi u ponudi: ");
            int index = 1;
            for (Item item : itemsSet) {
                System.out.println(index + ". " + item.getName());
                index++;
            }
            do {
                errorInput = false;
                try {
                    System.out.println("Koliko proizvoda želite dodati?");
                    numberOfItems = scanner.nextInt();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    errorInput = true;
                    scanner.nextLine();
                    logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                    System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                }
            } while (errorInput || numberOfItems < 1 || numberOfItems > itemsSet.size());

            //Item[] chosenItems = new Item[numberOfItems];

//            Item newItem = null;
//            do {
            for (int k = 0; k < numberOfItems; k++) {
                do {
                    errorInput = false;
                    try {
                        do {
                            System.out.println("Odaberite željeni proizvod: ");
                            chosenItemIndex = scanner.nextInt();
                            scanner.nextLine();
                        } while (chosenItemIndex < 1 || chosenItemIndex > itemsSet.size());
                        Item newItem = itemsSet.stream()
                                .skip(chosenItemIndex - 1)
                                .findFirst()
                                .orElseThrow();

//                        checkUniqueItem(newItem, chosenItems);
                        checkUniqueItemCategory(newItem, tempList);

                        chosenItems.add(newItem);
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    } catch (NoSuchElementException e) {
                        errorInput = true;
                        logger.error("Proizvod nije pronađen.", e);
                        System.out.println("Proizvod nije pronađen. Pokušajte ponovno.");
                    }
//                    catch (DuplicateItemSelectionException e) {
//                        errorInput = true;
//                        scanner.nextLine();
//                        logger.error("Korisnik je unio artikl koji je već odabran u tvornici.", e);
//                        System.out.println("Proizvod je već odabran u tvornici. Molimo pokušajte ponovno.");
//                    }
                    catch (DuplicateItemCategoryException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio artikl s kategorijom koja već postoji. ", e);
                        System.out.println("Odabrali ste aktikl s kategorijom koja već postoji. Molim pokušajte ponovno");
                    }
                } while (errorInput);

//                    Item newItem = items[chosenItemIndex - 1];


            }
//            } while (chosenItemIndex < 1 || chosenItemIndex > items.length);

            Factory newFactory = new Factory(name, address, chosenItems);
            factories[i] = newFactory;
        }
        return factories;
    }


    /**
     * Metoda za unos podataka o artiklima.
     *
     * @param scanner    Ulazna referenca za unos podataka.
     * @param categories Polje kategorija dostupnih za artikle.
     * @return Polje artikala unesenih od strane korisnika.
     */
    public static List<Item> enterItems(Scanner scanner, Category[] categories, FoodStore foodStore, TechnicalStore technicalStore) {
        Item[] items = new Item[5];
        Integer chosenCategoryIndex = -1;
        Integer chosenFoodIndex = -1;
        Integer discountAmount = -1;
        BigDecimal weight = BigDecimal.ZERO;

        Boolean errorInput;
        do {
            errorInput = false;
            try {
                System.out.println("Unesite popust koji se odnosi na sve artikle: ");
                discountAmount = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                errorInput = true;
                scanner.nextLine();
                logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
            }
        } while (errorInput);

        Discount discount = new Discount(discountAmount);

        System.out.println("Unesite pet artikala.");

        for (int i = 0; i < items.length; i++) {

            System.out.println("Kojoj kategoriji pripada artikal?");
            System.out.println("Kategorije: ");
            //Zasebna metoda?
            for (int j = 0; j < categories.length; j++) {
                System.out.println((j + 1) + ". " + categories[j].getName());
            }
            do {

                do {
                    errorInput = false;
                    try {
                        System.out.println("Odabir kategorije: ");
                        chosenCategoryIndex = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

            } while (chosenCategoryIndex < 1 || chosenCategoryIndex > categories.length);
            Category category = categories[chosenCategoryIndex - 1];

            if (category.getName().toLowerCase().contains("hrana")) {
                do {
                    do {
                        errorInput = false;
                        try {
                            System.out.println("Ponuđena hrana:\n" +
                                    "1. Banane\n" +
                                    "2. Jagode");
                            chosenFoodIndex = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            errorInput = true;
                            scanner.nextLine();
                            logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                            System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                        }
                    } while (errorInput);

                    if (chosenFoodIndex == 1) {
                        do {
                            errorInput = false;
                            try {
                                System.out.println("Unesite težinu: ");
                                weight = scanner.nextBigDecimal();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                errorInput = true;
                                scanner.nextLine();
                                logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                                System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                            }
                        } while (errorInput);


                        FoodItem newItem = new FoodItem("Banane", category, BigDecimal.valueOf(5), BigDecimal.valueOf(10),
                                BigDecimal.valueOf(5), BigDecimal.valueOf(10), BigDecimal.valueOf(11.99), discount, weight);

                        Double priceWithoutDiscount = newItem.calculatePrice(weight.doubleValue());
                        Double priceWithDiscount = priceWithoutDiscount * (1 - (discount.discountAmount() / 100));

                        System.out.println("Ukupne kalorije: " + newItem.calculateKilocalories());

                        if (discount.discountAmount() != 0) {
                            System.out.println("Ukupna cijena: " + priceWithoutDiscount);
                        } else {
                            System.out.println("Ukupna cijena: " + priceWithDiscount);
                        }

                        items[i] = newItem;

                        foodStore.addItem(newItem);
                    }
                    if (chosenFoodIndex == 2) {

                        do {
                            errorInput = false;
                            try {
                                System.out.println("Unesite težinu: ");
                                weight = scanner.nextBigDecimal();
                                scanner.nextLine();
                            } catch (InputMismatchException e) {
                                errorInput = true;
                                scanner.nextLine();
                                logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                                System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                            }
                        } while (errorInput);

                        FoodItem newItem = new FoodItem("Jagode", categories[0], BigDecimal.valueOf(3), BigDecimal.valueOf(5),
                                BigDecimal.valueOf(3), BigDecimal.valueOf(7), BigDecimal.valueOf(8.99), discount, weight);

                        Double priceWithoutDiscount = newItem.calculatePrice(weight.doubleValue());
                        Double priceWithDiscount = priceWithoutDiscount * (1 - (discount.discountAmount() / 100));


                        System.out.println("Ukupne kalorije: " + newItem.calculateKilocalories());
                        if (discount.discountAmount() != 0) {
                            System.out.println("Ukupna cijena: " + priceWithoutDiscount);
                        } else {
                            System.out.println("Ukupna cijena: " + priceWithDiscount);
                        }
                        items[i] = newItem;

                        foodStore.addItem(newItem);
                    }
                } while (chosenFoodIndex < 1 || chosenFoodIndex > 2);
            } else if (category.getName().toLowerCase().contains("laptop")) {
                BigDecimal width = BigDecimal.ZERO;
                BigDecimal height = BigDecimal.ZERO;
                BigDecimal length = BigDecimal.ZERO;
                BigDecimal productionCost = BigDecimal.ZERO;
                BigDecimal sellingPrice = BigDecimal.ZERO;
                Integer warrantyMonths = -1;

                System.out.println("Ime laptopa: ");
                String name = scanner.nextLine();
                do {
                    errorInput = false;
                    try {
                        System.out.println("Širina " + (i + 1) + ". artikla: ");
                        width = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                do {
                    errorInput = false;
                    try {
                        System.out.println("Visina " + (i + 1) + ". artikla: ");
                        height = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                do {
                    errorInput = false;
                    try {
                        System.out.println("Dužina " + (i + 1) + ". artikla: ");
                        length = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                do {
                    errorInput = false;
                    try {
                        System.out.println("Cijena proizvodnje: ");
                        productionCost = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                do {
                    errorInput = false;
                    try {
                        System.out.println("Maloprodajna cijena " + (i + 1) + ". artikla: ");
                        sellingPrice = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                do {
                    errorInput = false;
                    try {
                        System.out.println("Trajanje garancije u mjesecima: ");
                        warrantyMonths = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                Laptop newLaptop = new Laptop(name, category, width, height,
                        length, productionCost, sellingPrice, discount, warrantyMonths);

                items[i] = newLaptop;

                technicalStore.addItem(newLaptop);

            } else {
                BigDecimal width = BigDecimal.ZERO;
                BigDecimal height = BigDecimal.ZERO;
                BigDecimal length = BigDecimal.ZERO;
                BigDecimal productionCost = BigDecimal.ZERO;
                BigDecimal sellingPrice = BigDecimal.ZERO;

                System.out.println("Ime " + (i + 1) + " artikla: ");
                String name = scanner.nextLine();
                do {
                    errorInput = false;
                    try {
                        System.out.println("Širina " + (i + 1) + ". artikla: ");
                        width = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                do {
                    errorInput = false;
                    try {
                        System.out.println("Visina " + (i + 1) + ". artikla: ");
                        height = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                do {
                    errorInput = false;
                    try {
                        System.out.println("Dužina " + (i + 1) + ". artikla: ");
                        length = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                do {
                    errorInput = false;
                    try {
                        System.out.println("Cijena proizvodnje: ");
                        productionCost = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                do {
                    errorInput = false;
                    try {
                        System.out.println("Maloprodajna cijena " + (i + 1) + ". artikla: ");
                        sellingPrice = scanner.nextBigDecimal();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        errorInput = true;
                        scanner.nextLine();
                        logger.error("Korisnik je unio nedozvoljenu vrijednost. Potrebno je unijeti numeričku vrijednost.", e);
                        System.out.println("Unijeli ste nedozvoljenu vrijednost. Pokušajte ponovno.");
                    }
                } while (errorInput);

                Item newItem = new Item(name, category, width,
                        height, length, productionCost, sellingPrice, discount);

                items[i] = newItem;
            }
        }

        return Arrays.asList(items);
    }

    /**
     * Metoda omogućuje unos informacija za tri kategorije artikala putem korisničkog unosa.
     *
     * @param scanner Scanner objekt za unos korisnika.
     * @return Polje objekata tipa Category s unesenim kategorijama.
     */
    public static Category[] enterCategory(Scanner scanner) {
        Category[] categories = new Category[3];
        Boolean errorInput = false;

        String name;

        System.out.println("Unesite tri kategorije artikala.");
        for (int i = 0; i < categories.length; i++) {

            do {
                errorInput = false;
                System.out.println("Ime " + (i + 1) + ". kategorije: ");
                name = scanner.nextLine();

                try {
                    checkUniqueCategoryName(name, categories);
                } catch (DuplicateCategorySelectionException e) {
                    errorInput = true;
                    String errorMessage = "Unijeli ste naziv dvorane koji već postoji! Molim pokušajte ponovno. ";
                    logger.error(errorMessage, e);
                }
            } while (errorInput);
            System.out.println("Opis " + (i + 1) + ". kategorije: ");
            String description = scanner.nextLine();

            Category newCategory = new Category(name, description);
            categories[i] = newCategory;
        }

        return categories;
    }

    /**
     * Provjerava jedinstvenost unesenog imena kategorije u odnosu na već postojeće kategorije.
     *
     * @param newCategoryName Ime nove kategorije koje se provjerava.
     * @param categories      Polje objekata tipa Category u kojem se provjerava jedinstvenost imena.
     * @throws DuplicateCategorySelectionException Izuzetak koji se baca ako se unese duplicirano ime kategorije.
     */
    public static void checkUniqueCategoryName(String newCategoryName, Category[] categories) throws DuplicateCategorySelectionException {
        for (Category category : categories) {
            if (category != null) {
                if (category.getName().equals(newCategoryName)) {
                    throw new DuplicateCategorySelectionException("Korisnik je unio već postojeću kategoriju" + newCategoryName);
                }
            }
        }
    }

    /**
     * Metoda provjerava trgovinu koja prodaje najjeftiniji artikl među svim trgovinama.
     *
     * @param stores Polje objekata tipa Store u kojima se provjerava najniža cijena artikla.
     */
    public static void checkingArticlePrice(Store[] stores) {
        Optional<Store> storeWithLowestPrice = Optional.empty();
        BigDecimal lowestPrice = BigDecimal.valueOf(9999);
        for (Store store : stores) {
            for (Item item : store.getItems()) {
                BigDecimal price = item.getSellingPrice();
                if (price.compareTo(lowestPrice) < 0) {
                    lowestPrice = price;
                    storeWithLowestPrice = Optional.of(store);
                }
            }
        }
        if (storeWithLowestPrice.isPresent()) {
            System.out.println("Trgovina koja prodaje najjeftiniji artikl: " +
                    storeWithLowestPrice.get().getName());
        } else {
            System.out.println("Nema trgovine s najjeftinijim artiklom.");
        }
    }

    /**
     * Metoda pronalazi tvornicu koja proizvodi artikl s najvećim volumenom među svim tvornicama.
     *
     * @param factories Polje objekata tipa Factory koje se provjerava.
     */
    public static void checkingArticleVolume(Factory[] factories) {
        Optional<Factory> factoryWithLargestArticleVolume = Optional.empty();
        BigDecimal largestVolume = BigDecimal.ZERO;
        for (Factory factory : factories) {
            for (Item item : factory.getItems()) {
                BigDecimal volume = item.calculateVolume();
                if (volume.compareTo(largestVolume) > 0) {
                    largestVolume = volume;
                    factoryWithLargestArticleVolume = Optional.of(factory);
                }
            }
        }
        if (factoryWithLargestArticleVolume.isPresent()) {
            System.out.println("=====================");
            System.out.println("Tvornica koja proizvodi artikl s najvećim volumenom: " +
                    factoryWithLargestArticleVolume.get().getName());
            System.out.println("=====================");
        } else {
            System.out.println("Nema tvornice s najvećim artiklom volumena.");
            System.out.println("=====================");
        }
    }

    /**
     * Metoda provjerava laptop s najkraćim garantnim rokom među svim unesenim artiklima.
     *
     * @param itemsList Lista objekata tipa Item koje se provjerava.
     */
    public static void checkingShortestWarrantyLaptop(List<Item> itemsList) {
        int shortestWarrantyMonths = Integer.MAX_VALUE;
        Optional<Laptop> shortestWarrantyLaptop = Optional.empty();

        for (Item item : itemsList) {
            if (item instanceof Laptop laptop) {
                int warrantyMonths = laptop.getWarrantyMonths();

                if (warrantyMonths < shortestWarrantyMonths) {
                    shortestWarrantyMonths = warrantyMonths;
                    shortestWarrantyLaptop = Optional.of(laptop);
                }
            }
        }

        if (shortestWarrantyLaptop.isPresent()) {
            System.out.println("=====================");
            System.out.println("Laptop s najkraćim garantnim rokom:");
            System.out.println("Ime: " + shortestWarrantyLaptop.get().getName());
            System.out.println("Garantni rok u mjesecima: " + shortestWarrantyLaptop.get().getWarrantyMonths());
        } else {
            System.out.println("Nema unesenih laptopa ili nema laptopa s garantnim rokom.");
        }
        System.out.println("=====================");

    }

    /**
     * Metoda provjerava namirnicu s najviše kalorija i namirnicu s najvećom ukupnom cijenom
     * među svim unesenim artiklima.
     *
     * @param itemsList Lista objekata tipa Item koje se provjerava.
     */
    public static void checkingArticleKilocaloriesAndPrice(List<Item> itemsList) {
        Integer maxCalories = Integer.MIN_VALUE;
        Double maxPrice = Double.MIN_VALUE;
        Double minPrice = Double.MAX_VALUE;
        Optional<FoodItem> maxCaloriesFood = Optional.empty();
        Optional<FoodItem> maxPriceFood = Optional.empty();
        Optional<FoodItem> minPriceFood = Optional.empty();

        for (Item item : itemsList) {
            if (item instanceof FoodItem foodItem) {
                Integer calories = foodItem.calculateKilocalories();
                Double price = foodItem.calculatePrice(foodItem.getWeight().doubleValue());

                if (calories > maxCalories) {
                    maxCalories = calories;
                    maxCaloriesFood = Optional.of(foodItem);
                }
                if (price > maxPrice) {
                    maxPrice = price;
                    maxPriceFood = Optional.of(foodItem);
                }
                if (price < minPrice) {
                    minPrice = price;
                    minPriceFood = Optional.of(foodItem);
                }
            }
        }

        if (maxCaloriesFood.isPresent()) {
            System.out.println("=====================");
            System.out.println("Namirnica s najviše kalorija: " + maxCaloriesFood.get().getName());
            System.out.println("Broj kalorija: " + maxCalories);
        } else {
            System.out.println("=====================");
            System.out.println("Nema namirnice s najviše kalorija.");
        }

        if (maxPriceFood.isPresent()) {
            System.out.println();
            System.out.println("Namirnica s najvećom ukupnom cijenom: " + maxPriceFood.get().getName());
            System.out.println("Ukupna cijena: " + maxPrice);
            System.out.println("Dodatni podatci o artiklu: ");
            System.out.println(maxPriceFood);
            System.out.println();
        } else {
            System.out.println("Nema namirnice s najvećom cijenom.");
        }

        if (minPriceFood.isPresent()) {
            System.out.println("Namirnica s najmanjom ukupnom cijenom: " + minPriceFood.get().getName());
            System.out.println("Ukupna cijena: " + minPrice);
            System.out.println("Dodatni podatci o artiklu: ");
            System.out.println(minPriceFood);
            System.out.println();
        }
    }

    public static void checkingLaptopPrice(List<Item> itemsList) {
        Double maxPrice = Double.MIN_VALUE;
        Double minPrice = Double.MAX_VALUE;
        Optional<Laptop> maxPriceLaptop = Optional.empty();
        Optional<Laptop> minPriceLaptop = Optional.empty();

        for (Item item : itemsList) {
            if (item instanceof Laptop laptop) {
                Double price = laptop.calculatePrice();

                if (price > maxPrice) {
                    maxPrice = price;
                    maxPriceLaptop = Optional.of(laptop);
                }

                if (price < minPrice) {
                    minPrice = price;
                    minPriceLaptop = Optional.of(laptop);
                }
            }
        }

        if (maxPriceLaptop.isPresent()) {
            System.out.println("=====================");
            System.out.println("Najskuplji laptop: " + maxPriceLaptop.get().getName());
            System.out.println("Ukupna cijena: " + maxPrice);
            System.out.println("Dodatni podatci o laptopu: ");
            System.out.println(maxPriceLaptop);
            System.out.println();
        } else {
            System.out.println("=====================");
            System.out.println("Nema dostupnih laptopa.");
        }

        if (minPriceLaptop.isPresent()) {
            System.out.println("Najjeftiniji laptop: " + minPriceLaptop.get().getName());
            System.out.println("Ukupna cijena: " + minPrice);
            System.out.println("Dodatni podatci o laptopu: ");
            System.out.println(minPriceLaptop);
            System.out.println();
        } else {
            System.out.println("Nema dostupnih laptopa.");
        }
    }


    /**
     * Provjerava jedinstvenost unesenog artikla u odnosu na već postojeće artikle.
     *
     * @param newItem   Novi artikl koji se provjerava.
     * @param itemsList Lista objekata tipa Item u kojem se provjerava jedinstvenost artikla.
     * @return True ako je artikl jedinstven, inače False.
     * @throws DuplicateItemSelectionException Izuzetak koji se baca ako se unese već postojeći artikl.
     */
    public static void checkUniqueItem(Item newItem, List<Item> itemsList) throws DuplicateItemSelectionException {
        if (itemsList.contains(newItem)) {
            throw new DuplicateItemSelectionException("Korisnik je unio već postojeći artikl. " + newItem.getName());
        }
    }

    public static void checkUniqueItemCategory(Item newItem, List<Item> itemsList) {
        for (Item item : itemsList) {
            if (item != null && item.getCategory().equals(newItem.getCategory())) {
                throw new DuplicateItemCategoryException("Korisnik je odabrao artikl s već postojećom kategorijom. " + newItem.getName());
            }
        }
    }

    public static void itemsByCategory(List<Item> itemsList) {
        Map<Category, List<Item>> itemsByCategory = new HashMap<>();

        for (Item item : itemsList) {
            itemsByCategory.computeIfAbsent(item.getCategory(), k -> new ArrayList<>()).add(item);
        }


        for (List<Item> itemList : itemsByCategory.values()) {
            itemList.sort(new ProductionSorter(true));
        }

        for (Map.Entry<Category, List<Item>> entry : itemsByCategory.entrySet()) {
            Category category = entry.getKey();
            List<Item> itemsInCategory = entry.getValue();

            if (!itemsInCategory.isEmpty()) {
                Item cheapestItem = itemsInCategory.get(0);
                System.out.println("Najjeftiniji proizvod u kategoriji '" + category + "': " + cheapestItem.getName());

                Item mostExpensiveItem = itemsInCategory.get(itemsInCategory.size() - 1);
                System.out.println("Najskuplji proizvod u kategoriji '" + category + "': " + mostExpensiveItem.getName());
            }
            System.out.println("=====================");
        }
    }

    public static void sortItemsByVolume(List<Item> items) {
        items.sort(Comparator.comparing(Item::calculateVolume));
    }

    public static void sortItemsByVolumeWithoutLambda(List<Item> items) {
        Collections.sort(items, new VolumeComparator());
    }

    public static BigDecimal calculateAverageVolume(List<Item> items) {
        BigDecimal averageVolume = items.stream()
                .map(Item::calculateVolume)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return averageVolume.divide(BigDecimal.valueOf(items.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calculateAverageVolumeWithoutLambda(List<Item> items) {
        BigDecimal sum = BigDecimal.ZERO;

        for (Item item : items) {
            sum = sum.add(item.calculateVolume());
        }

        return sum.divide(BigDecimal.valueOf(items.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calculateAveragePriceForAboveAverageVolume(List<Item> items) {
        BigDecimal averageVolume = calculateAverageVolume(items);

        BigDecimal averagePrice = items.stream()
                .filter(item -> item.calculateVolume().compareTo(averageVolume) > 0)
                .map(Item::getSellingPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return averagePrice.divide(BigDecimal.valueOf(items.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal calculateAveragePriceForAboveAverageVolumeWithoutLambda(List<Item> items) {
        BigDecimal averageVolume = calculateAverageVolumeWithoutLambda(items);
        BigDecimal sum = BigDecimal.ZERO;
        int count = 0;

        for (Item item : items) {
            if (item.calculateVolume().compareTo(averageVolume) > 0) {
                sum = sum.add(item.getSellingPrice());
                count++;
            }
        }

        return count == 0 ? BigDecimal.ZERO :
                sum.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
    }

    public static List<Store> aboveAverageNumberOfItems(Store[] stores) {
        double averageItemCount = Arrays.stream(stores)
                .flatMap(store -> store.getItems().stream())
                .count() / (double) stores.length;

        return Arrays.stream(stores)
                .filter(store -> store.getItems().size() > averageItemCount)
                .collect(Collectors.toList());
    }

    public static List<Store> aboveAverageNumberOfItemsWithoutLambda(Store[] stores) {
        double totalItemCount = 0;

        for (Store store : stores) {
            totalItemCount += store.getItems().size();
        }

        double averageItemCount = totalItemCount / stores.length;
        List<Store> aboveAverageStores = new ArrayList<>();

        for (Store store : stores) {
            if (store.getItems().size() > averageItemCount) {
                aboveAverageStores.add(store);
            }
        }

        return aboveAverageStores;
    }

    public static void printNumberOfItems(Store[] stores) {
        Arrays.stream(stores)
                .forEach(store -> System.out.println("Broj artikala u trgovini " + store.getName() + "= " + store.getItems().size()));
    }
}
