import {
  StyleSheet,
  Text,
  SafeAreaView,
  Dimensions,
  View,
  Button,
} from "react-native";
import React, { useState } from "react";
import colors from "../config/colors";
import Header from "../components/Header";
import Counter from "../components/Counter";
import Carousal from "../components/Carousal";

const dimensions = Dimensions.get("screen");

const Product = () => {
  const productData = {
    productId: "6d000c14-b199-44b0-aab9-1b24cd0a33b0",
    merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
    name: "Basic Chair",
    images: [
      "https://i.etsystatic.com/30457381/r/il/e10354/4169856402/il_fullxfull.4169856402_4wo1.jpg",
      "https://thermaltake-de-bhgycxg9djfgcmfn.a02.azurefd.net/media/catalog/product/cache/023a745bb14092c479b288481f91a1bd/x/c/xcomfort_blackred01.jpg",
      "https://sofaland.ca/cdn/shop/products/ND_SL_DORY_CHAIR_FRONT_IMG_1044_989152fe-4cf4-425b-991d-91069ba48b22.jpg?v=1633464870",
      "https://m.media-amazon.com/images/I/81fOwaujXaL.jpg",
      "https://cdn.thewirecutter.com/wp-content/media/2024/03/pipersongchair-2048px-PXL_20240226_205609504.jpg?auto=webp&quality=60&width=570",
    ],
    description:
      "Basic Chair a chair that is comfortable and cost effective bringing you from company known for being a user's company.",
    price: 50,
    currencyCode: "CAD",
    categories: ["classic"],
    reviews: [],
    createdAt: 1739302696589,
    updatedAt: 1739302696589,
    status: "PENDING",
    attributes: {
      color: "red",
      height: 100,
      width: 50,
    },
    quantityAvailable: 250,
    quantitySold: 0,
    quantityReserved: 0,
  };

  const [quantity, setQuanity] = useState(0);

  const onCounterChange = (value) => {
    setQuanity(value);
  };

  const onClickHandler = () => {
    console.log("AR Implementation");
  };

  const addToCart = () => {
    console.log("Add to cart:" + quantity);
  };

  return (
    <SafeAreaView style={styles.container}>
      <Header />
      <View style={styles.minicontainer}>
        <Carousal images={productData.images} />
        <Button
          onPress={onClickHandler}
          title="View in AR"
          color={colors.secondary}
          accessibilityLabel="View the product in your home"
        />
        <View style={styles.info}>
          <Text style={styles.title}>{productData.name}</Text>
          <Text style={styles.subtitle}>
            {productData.currencyCode} {productData.price}
          </Text>
          <View style={styles.attributes}>
            <Text>Quantity:</Text>
            <Counter
              maxQuantity={productData.quantityAvailable}
              onChangeEventHandler={onCounterChange}
            />
          </View>
          {Object.entries(productData.attributes).map(([key, value], index) => (
            <View style={styles.attributes} key={index}>
              <Text style={styles.uppercase}>{key}:</Text>
              <Text style={styles.uppercase}>{value}</Text>
            </View>
          ))}
          <View style={styles.description}>
            <Text>Description:</Text>
            <Text>{productData.description}</Text>
          </View>
          <Button
            onPress={addToCart}
            title="Add to Cart"
            color={colors.secondary}
            accessibilityLabel="Add the product to cart"
          />
        </View>
      </View>
    </SafeAreaView>
  );
};

export default Product;

const styles = StyleSheet.create({
  container: {
    width: dimensions.width,
    height: dimensions.height,
    backgroundColor: colors.primary,
  },
  minicontainer: {
    height: "100%",
    width: "100%",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    flexDirection: "column",
  },
  info: {
    display: "flex",
    alignItems: "center",
    flexDirection: "column",
    width: "100%",
    height: "50%"
  },
  title: {
    fontSize: 24,
    fontWeight: 800,
    marginBottom: "2%",
  },
  subtitle: {
    fontSize: 18,
    fontWeight: 700,
    marginBottom: "2%",
  },
  attributes: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    width: "90%",
    marginBottom: "1%",
  },
  uppercase: {
    textTransform: "capitalize",
  },
  description: {
    width: "90%",
    marginBottom: "1%",
  },
});
