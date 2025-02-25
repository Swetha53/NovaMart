import {
  StyleSheet,
  Text,
  SafeAreaView,
  Button,
  Image,
  Dimensions,
  ScrollView,
  Pressable,
} from "react-native";
import React, { useState } from "react";
import colors from "../config/colors";
import imagePlaceholder from "../assets/image.png";
import upArrow from "../assets/up_arrow.png";
import downArrow from "../assets/down_arrow.png";

const OrderTable = (props) => {
  const { order, name } = props;
  const [showItemList, setShowItemList] = useState(false);
  const onClickHandler = () => {
    console.log("clicked");
  };
  const writeReview = () => {
    console.log("review");
  };
  const toggleShowItemList = () => {
    setShowItemList(!showItemList);
  };
  return (
    <SafeAreaView style={styles.container}>
      <SafeAreaView style={styles.header}>
        <SafeAreaView style={styles.headerrow}>
          <Text style={styles.headerlabel}>Order Placed:</Text>
          <Text style={styles.headertext}>{order.createdAt}</Text>
        </SafeAreaView>
        <SafeAreaView style={styles.headerrow}>
          <Text style={styles.headerlabel}>Total:</Text>
          <Text style={styles.headertext}>
            {order.currencyCode} {order.totalAmount}
          </Text>
        </SafeAreaView>
        <SafeAreaView style={styles.headerrow}>
          <Text style={styles.headerlabel}>Ship To:</Text>
          <Text style={styles.headertext}>{name}</Text>
        </SafeAreaView>
        <SafeAreaView style={styles.headerrow}>
          <Text style={styles.headerlabel}>Order </Text>
          <Text style={styles.headertext}>#{order.orderId}</Text>
        </SafeAreaView>
        <SafeAreaView style={styles.headerrow}>
          <Pressable
            style={styles.headerarrowpress}
            onPress={toggleShowItemList}
          >
            {showItemList ? (
              <Image source={upArrow} style={styles.headerarrow} />
            ) : (
              <Image source={downArrow} style={styles.headerarrow} />
            )}
          </Pressable>
          <Button
            onPress={onClickHandler}
            title="Get Product Support"
            color={colors.primary}
            accessibilityLabel="Get product support"
          />
        </SafeAreaView>
      </SafeAreaView>
      {showItemList && (
        <SafeAreaView style={styles.body}>
          {order.orderItemList.map((item, idx) => (
            <SafeAreaView style={styles.itemrow} key={idx}>
              <Image source={imagePlaceholder} style={styles.itemimage} />
              <SafeAreaView style={styles.itemrightcell}>
                <Text>
                  {item.productId} (x {item.quantity})
                </Text>
                <Text>
                  {order.currencyCode} {item.totalPrice}
                </Text>
                <Button
                  onPress={writeReview}
                  title="Write Product Review"
                  color={colors.secondary}
                  accessibilityLabel="Product Review"
                />
              </SafeAreaView>
            </SafeAreaView>
          ))}
        </SafeAreaView>
      )}
    </SafeAreaView>
  );
};

export default OrderTable;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    marginBottom: "5%",
  },
  header: {
    width: "100%",
    backgroundColor: colors.secondary,
    display: "flex",
    alignItems: "center",
    borderTopRightRadius: "3%",
    borderTopLeftRadius: "3%",
  },
  headerrow: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    width: "95%",
    marginTop: "1%",
  },
  headerlabel: {
    width: "30%",
  },
  headertext: {
    width: "70%",
  },
  headerarrowpress: {
    width: "5%",
    aspectRatio: 1 / 1,
  },
  headerarrow: {
    height: "100%",
    aspectRatio: 1 / 1,
  },
  body: {
    marginTop: "2%",
    borderStyle: "solid",
    borderColor: colors.secondary,
    borderWidth: 1,
    borderBottomLeftRadius: "3%",
    borderBottomRightRadius: "3%",
    display: "flex",
    alignItems: "center",
  },
  itemrow: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
    width: "95%",
  },
  itemrightcell: {
    width: "85%",
  },
  itemimage: {
    width: "10%",
    aspectRatio: 1 / 1,
  },
});
