const sampleProducts = [
  {
    productId: "8989f64c-0ddc-4416-94f4-cd7cadc32131",
    merchantId: "d8714737-5b3c-49ea-9042-4e83bb599972",
    name: "Swivel Chair",
    images: [
      "https://www.ikea.com/ca/en/images/products/tossberg-malskaer-swivel-chair-grann-light-brown-black__1199989_pe904797_s5.jpg?f=xl",
      "https://www.ikea.com/ca/en/images/products/tossberg-malskaer-swivel-chair-grann-light-brown-black__1199986_pe904796_s5.jpg?f=xl",
      "https://www.ikea.com/ca/en/images/products/tossberg-malskaer-swivel-chair-grann-light-brown-black__1199987_pe904799_s5.jpg?f=xl",
      "https://www.ikea.com/ca/en/images/products/tossberg-malskaer-swivel-chair-grann-light-brown-black__1199988_pe904798_s5.jpg?f=xl",
    ],
    description:
      "Swivel chair with soft shapes and nice padding. Upholstered in durable leather that blends well in all types of rooms – and with casters that make it easy to move.",
    price: 379,
    currencyCode: "CAD",
    categories: ["office"],
    reviews: [
      {
        reviewId: "37a6887d-ed1c-43f8-aac5-a80fcdd761cb",
        userId: "eec0e9f9-8247-4d8a-8ada-df48116e818d",
        merchantId: "d8714737-5b3c-49ea-9042-4e83bb599972",
        productId: "8989f64c-0ddc-4416-94f4-cd7cadc32131",
        title: "Good Product",
        comment: "Decent product quality for the price.",
        imageUrl: [],
        rating: 3,
      },
      {
        reviewId: "257b436c-917a-47a4-9c19-e8fa078fae9b",
        userId: "eec0e9f9-8247-4d8a-8ada-df48116e818d",
        merchantId: "d8714737-5b3c-49ea-9042-4e83bb599972",
        productId: "8989f64c-0ddc-4416-94f4-cd7cadc32131",
        title: "Useless Product",
        comment:
          "Broke after using for a few times. Not worth the money and wait.",
        imageUrl: [],
        rating: 1,
      },
    ],
    createdAt: 1741556303443,
    updatedAt: 1741556303443,
    status: "PENDING",
    attributes: {
      color: "light brown",
      height: 100,
      width: 67,
    },
    quantityAvailable: 235,
    quantitySold: 2,
    quantityReserved: 13,
  },
];

const sampleUser = [
  {
    userId: "demo",
    email: "test@hotmail.com",
    password: "Strong_pa55word",
    firstName: "Test",
    lastName: "User",
    age: 100,
    gender: "F",
    phone: "1234567890",
    address: "2nd floor, Ravenclaw Common Room, Hogwarts, Scotland",
    avatar: "",
    roles: ["VIEW"],
    accountType: "CUSTOMER",
    preferences: [],
    createdAt: 1743798883096,
    updatedAt: 1743799492983,
  },
];

const sampleReviews = [
  {
    reviewId: "37a6887d-ed1c-43f8-aac5-a80fcdd761cb",
    userId: "demo",
    merchantId: "d8714737-5b3c-49ea-9042-4e83bb599972",
    productId: "8989f64c-0ddc-4416-94f4-cd7cadc32131",
    title: "Good Product",
    comment: "Decent product quality for the price.",
    imageUrl: [],
    rating: 3,
  },
  {
    reviewId: "257b436c-917a-47a4-9c19-e8fa078fae9b",
    userId: "demo",
    merchantId: "d8714737-5b3c-49ea-9042-4e83bb599972",
    productId: "8989f64c-0ddc-4416-94f4-cd7cadc32131",
    title: "Useless Product",
    comment: "Broke after using for a few times. Not worth the money and wait.",
    imageUrl: [],
    rating: 1,
  },
];

const sampleOrders = [
  {
    orderId: "73bbcae4-9905-4c0f-b5bb-627e39f1bd35",
    userId: "eec0e9f9-8247-4d8a-8ada-df48116e818d",
    customerName: "CONFIRMED",
    orderStatus: "Tom Riddle",
    totalAmount: 438.9,
    currencyCode: "CAD",
    createdAt: 1744144444373,
    orderItemList: [
      {
        orderItemId: "6361ac6c-fd70-4231-b2e1-4ad08d88f56c",
        orderId: "73bbcae4-9905-4c0f-b5bb-627e39f1bd35",
        productId: "ce54d473-2c03-4a15-bda1-80dba07a0fbe",
        merchantId: "d8714737-5b3c-49ea-9042-4e83bb599972",
        quantity: 1,
        unitPrice: 399.0,
        totalPrice: 399.0,
        createdAt: 1744144444373,
      },
    ],
  },
  {
    orderId: "8b4a040e-c99b-4962-aaf5-89be5e51a971",
    userId: "eec0e9f9-8247-4d8a-8ada-df48116e818d",
    customerName: "CONFIRMED",
    orderStatus: "Tom Riddle",
    totalAmount: 635.8,
    currencyCode: "CAD",
    createdAt: 1744144211386,
    orderItemList: [
      {
        orderItemId: "a80fc4b3-ca6e-41c0-bc9f-52720cde6f3e",
        orderId: "8b4a040e-c99b-4962-aaf5-89be5e51a971",
        productId: "98f2988c-6760-434c-8f62-69721c8c5877",
        merchantId: "d8714737-5b3c-49ea-9042-4e83bb599972",
        quantity: 1,
        unitPrice: 199.0,
        totalPrice: 199.0,
        createdAt: 1744144211408,
      },
      {
        orderItemId: "da584974-4f2f-4a10-ad1a-58140d62f6a4",
        orderId: "8b4a040e-c99b-4962-aaf5-89be5e51a971",
        productId: "8989f64c-0ddc-4416-94f4-cd7cadc32131",
        merchantId: "d8714737-5b3c-49ea-9042-4e83bb599972",
        quantity: 1,
        unitPrice: 379.0,
        totalPrice: 379.0,
        createdAt: 1744144211386,
      },
    ],
  },
];

const sampleAsset = {
  data: [
    {
      asset_id: "82b7902c-2d08-4152-8278-571288adf7ba",
      created_at: "2025-03-18T19:58:15.009891+00:00",
      product_id: "8989f64c-0ddc-4416-94f4-cd7cadc32131",
      asset_url:
        "https://reggvbnnkqmprlkojomx.supabase.co/storage/v1/object/public/Products/8989f64c-0ddc-4416-94f4-cd7cadc32131.glb?",
    },
  ],
  count: null,
};

export { sampleProducts, sampleUser, sampleReviews, sampleOrders, sampleAsset };
