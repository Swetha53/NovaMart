import {
  StyleSheet,
  Text,
  SafeAreaView,
  Dimensions,
  Image,
  Pressable,
  ScrollView,
} from "react-native";
import React, { useState } from "react";
import Header from "../components/Header";
import colors from "../config/colors";
import star from "../assets/star.png";
import filledStar from "../assets/fill_star.png";
import imagePlaceholder from "../assets/image.png";
import OrderTable from "../components/OrderTable";

const dimensions = Dimensions.get("screen");

const Profile = ({ navigation }) => {
  const [activeTab, setActiveTab] = useState(0);
  const userData = {
    userId: "9633ec2b-7e0e-466e-866a-159afccf7542",
    email: "remusjohnlupin@hotmail.com",
    password: "moony_0",
    firstName: "Remus",
    lastName: "Lupin",
    age: 40,
    gender: "M",
    phone: "5432109876",
    address: "2nd floor, Gryffindor Common Room, Hogwarts, Scotland",
    avatar:
      "https://cinema-quotes.com/wp-content/uploads/2024/05/Professor-Lupin.jpg",
    roles: ["VIEW"],
    accountType: "CUSTOMER",
    preferences: ["classic"],
    createdAt: 1738705478645,
    updatedAt: 1739326271052,
  };
  const userReviews = [
    {
      reviewId: "b221c2b2-0d3d-4895-a1c6-c9918da3d83f",
      userId: "9633ec2b-7e0e-466e-866a-159afccf7542",
      merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
      productId: "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
      title: "Useless Product",
      comment:
        "Broke after using for a few times. Not worth the money and wait.",
      imageUrl: [],
      rating: 1,
    },
    {
      reviewId: "b221c2b2-0d3d-4895-a1c6-c9918da3d83f",
      userId: "9633ec2b-7e0e-466e-866a-159afccf7542",
      merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
      productId: "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
      title: "Useless Product",
      comment:
        "Broke after using for a few times. Not worth the money and wait.",
      imageUrl: [],
      rating: 1,
    },
    {
      reviewId: "b221c2b2-0d3d-4895-a1c6-c9918da3d83f",
      userId: "9633ec2b-7e0e-466e-866a-159afccf7542",
      merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
      productId: "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
      title: "Useless Product",
      comment:
        "Broke after using for a few times. Not worth the money and wait.",
      imageUrl: [],
      rating: 1,
    },
  ];
  const orders = [
    {
      orderId: "cb55c108-af1a-4a13-9169-15226cfd7253",
      userId: "9633ec2b-7e0e-466e-866a-159afccf7542",
      orderStatus: "DELIVERED",
      totalAmount: 230.0,
      currencyCode: "CAD",
      createdAt: 1738788348035,
      orderItemList: [
        {
          orderItemId: "757eecad-0e5d-4f71-8e85-e8c52f4350c6",
          orderId: "cb55c108-af1a-4a13-9169-15226cfd7253",
          productId: "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
          merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
          quantity: 2,
          unitPrice: 50.0,
          totalPrice: 100.0,
          createdAt: 1738788348036,
        },
        {
          orderItemId: "757eecad-0e5d-4f71-8e85-e8c52f4350c6",
          orderId: "cb55c108-af1a-4a13-9169-15226cfd7253",
          productId: "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
          merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
          quantity: 2,
          unitPrice: 50.0,
          totalPrice: 100.0,
          createdAt: 1738788348036,
        },
        {
          orderItemId: "757eecad-0e5d-4f71-8e85-e8c52f4350c6",
          orderId: "cb55c108-af1a-4a13-9169-15226cfd7253",
          productId: "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
          merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
          quantity: 2,
          unitPrice: 50.0,
          totalPrice: 100.0,
          createdAt: 1738788348036,
        },
      ],
      },
      {
        orderId: "cb55c108-af1a-4a13-9169-15226cfd7253",
        userId: "9633ec2b-7e0e-466e-866a-159afccf7542",
        orderStatus: "DELIVERED",
        totalAmount: 230.0,
        currencyCode: "CAD",
        createdAt: 1738788348035,
        orderItemList: [
          {
            orderItemId: "757eecad-0e5d-4f71-8e85-e8c52f4350c6",
            orderId: "cb55c108-af1a-4a13-9169-15226cfd7253",
            productId: "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
            merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
            quantity: 2,
            unitPrice: 50.0,
            totalPrice: 100.0,
            createdAt: 1738788348036,
          },
          {
            orderItemId: "757eecad-0e5d-4f71-8e85-e8c52f4350c6",
            orderId: "cb55c108-af1a-4a13-9169-15226cfd7253",
            productId: "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
            merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
            quantity: 2,
            unitPrice: 50.0,
            totalPrice: 100.0,
            createdAt: 1738788348036,
          },
          {
            orderItemId: "757eecad-0e5d-4f71-8e85-e8c52f4350c6",
            orderId: "cb55c108-af1a-4a13-9169-15226cfd7253",
            productId: "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
            merchantId: "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
            quantity: 2,
            unitPrice: 50.0,
            totalPrice: 100.0,
            createdAt: 1738788348036,
          },
        ],
      },
  ];

  const tabChange = (value) => {
    setActiveTab(value);
    // if (value == 1) {
    //   loadUserOrders();
    // }
  };
  return (
    <SafeAreaView>
      <Header />
      <SafeAreaView style={styles.container}>
        <SafeAreaView style={styles.pageheader}>
          <Image source={{ uri: userData.avatar }} style={styles.avatar} />
          <Pressable
            onPress={() => {
              tabChange(0);
            }}
            style={[styles.tab, activeTab == 0 ? {} : styles.passiveTab]}
          >
            <Text>Profile</Text>
          </Pressable>
          <Pressable
            onPress={() => {
              tabChange(1);
            }}
            style={[styles.tab, activeTab == 1 ? {} : styles.passiveTab]}
          >
            <Text>Order</Text>
          </Pressable>
          <Pressable
            onPress={() => {
              tabChange(2);
            }}
            style={[
              styles.tab,
              activeTab == 2 ? {} : styles.passiveTab,
              styles.disabledTab,
            ]}
          >
            <Text>Wishlist</Text>
          </Pressable>
        </SafeAreaView>
        {activeTab == 0 && (
          <SafeAreaView style={styles.infocontainer}>
            <SafeAreaView style={styles.inforow}>
              <Text style={styles.infolabel}>First Name:</Text>
              <Text style={styles.infotext}>{userData.firstName}</Text>
            </SafeAreaView>
            <SafeAreaView style={styles.inforow}>
              <Text style={styles.infolabel}>Last Name:</Text>
              <Text style={styles.infotext}>{userData.lastName}</Text>
            </SafeAreaView>
            <SafeAreaView style={styles.inforow}>
              <Text style={styles.infolabel}>Age:</Text>
              <Text style={styles.infotext}>{userData.age}</Text>
            </SafeAreaView>
            <SafeAreaView style={styles.inforow}>
              <Text style={styles.infolabel}>Gender:</Text>
              <Text style={styles.infotext}>{userData.gender}</Text>
            </SafeAreaView>
            <SafeAreaView style={styles.inforow}>
              <Text style={styles.infolabel}>Email:</Text>
              <Text style={styles.infotext}>{userData.email}</Text>
            </SafeAreaView>
            <SafeAreaView style={styles.inforow}>
              <Text style={styles.infolabel}>Address:</Text>
              <Text style={styles.infotext}>{userData.address}</Text>
            </SafeAreaView>
            <SafeAreaView style={styles.reviewcontainer}>
              <ScrollView contentContainerStyle={styles.reviewscrollcontainer}>
                <Text style={styles.reviewheading}>Your Reviews:</Text>
                {userReviews.map((userReview, idx) => (
                  <SafeAreaView key={idx} style={styles.reviewminicontainer}>
                    <SafeAreaView style={styles.ratingcontainer}>
                      {Array.from(
                        { length: userReview.rating },
                        (_, i) => i + 1
                      ).map((num) => (
                        <Image
                          source={filledStar}
                          key={"fill-" + num}
                          style={styles.rating}
                        />
                      ))}
                      {Array.from(
                        { length: 5 - userReview.rating },
                        (_, i) => i + 1
                      ).map((num) => (
                        <Image
                          source={star}
                          key={"unfilled-" + num}
                          style={styles.rating}
                        />
                      ))}
                    </SafeAreaView>
                    <Text style={styles.reviewtitle}>{userReview.title}</Text>
                    <Text style={styles.reviewcomment}>
                      {userReview.comment}
                    </Text>
                    <SafeAreaView style={styles.reviewfooter}>
                      {userReview.imageUrl && userReview.imageUrl.length > 0 ? (
                        <Image
                          source={{ uri: userReview.imageUrl[0] }}
                          style={styles.reviewimage}
                        />
                      ) : (
                        <Image
                          source={imagePlaceholder}
                          style={styles.reviewimage}
                        />
                      )}
                      <Pressable
                        onPress={() => {
                          navigation.navigate("Product");
                        }}
                      >
                        <Text style={styles.reviewlink}>See Product</Text>
                      </Pressable>
                    </SafeAreaView>
                  </SafeAreaView>
                ))}
              </ScrollView>
            </SafeAreaView>
          </SafeAreaView>
        )}
        {activeTab == 1 && (
          <SafeAreaView style={styles.ordercontainer}>
            <ScrollView style={styles.orderscrollcontainer}>
              {orders.map((order, idx) => (
                <OrderTable
                  order={order}
                  key={idx}
                  name={userData.firstName + " " + userData.lastName}
                />
              ))}
            </ScrollView>
          </SafeAreaView>
        )}
      </SafeAreaView>
    </SafeAreaView>
  );
};

export default Profile;

const styles = StyleSheet.create({
  container: {
    backgroundColor: colors.primary,
    width: dimensions.width,
    height: dimensions.height,
    display: "flex",
    alignItems: "center",
  },
  pageheader: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-around",
    alignItems: "center",
    marginTop: "2%",
    marginBottom: "2%",
    width: "95%",
  },
  avatar: {
    backgroundColor: colors.secondary,
    width: "25%",
    aspectRatio: 1 / 1,
    borderRadius: "50%",
  },
  tab: {
    backgroundColor: colors.secondary,
    width: "20%",
    aspectRatio: 3 / 1,
    display: "flex",
    flexDirection: "row",
    justifyContent: "center",
    alignItems: "center",
    borderRadius: "15%",
  },
  passiveTab: {
    backgroundColor: colors.primary,
    borderColor: colors.secondary,
    borderWidth: 1,
    borderStyle: "solid",
  },
  disabledTab: {
    pointerEvents: "none",
    opacity: 0.4,
  },
  infocontainer: {
    width: "90%",
  },
  inforow: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-between",
    width: "100%",
    marginTop: "1%",
    marginBottom: "1%",
  },
  infolabel: {
    fontSize: 18,
    fontWeight: 500,
    width: "30%",
  },
  infotext: {
    fontSize: 18,
    width: "70%",
  },
  reviewcontainer: {
    width: "100%",
    height: "50%",
    marginTop: "1%",
    backgroundColor: colors.secondary,
    borderRadius: "8%",
    display: "flex",
    alignItems: "center",
  },
  reviewscrollcontainer: {
    display: "flex",
    alignItems: "center",
  },
  reviewheading: {
    fontSize: 18,
    fontWeight: 600,
    textDecorationStyle: "solid",
    textDecorationLine: "underline",
  },
  reviewminicontainer: {
    width: "90%",
    aspectRatio: 2 / 1,
    backgroundColor: colors.primary,
    borderRadius: "8%",
    margin: "2%",
    display: "flex",
    alignItems: "center",
  },
  ratingcontainer: {
    height: "20%",
    width: "90%",
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-evenly",
    alignItems: "center",
  },
  rating: {
    width: "7%",
    aspectRatio: 1 / 1,
  },
  reviewtitle: {
    width: "90%",
    fontSize: 16,
    fontWeight: 500,
    maxHeight: "10%",
  },
  reviewcomment: {
    width: "90%",
    height: "45%",
  },
  reviewfooter: {
    display: "flex",
    height: "20%",
    width: "90%",
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  reviewimage: {
    width: "10%",
    aspectRatio: 1 / 1,
  },
  reviewlink: {
    color: colors.secondary,
    textDecorationStyle: "solid",
    textDecorationLine: "underline",
    fontSize: 16,
    fontWeight: 600,
  },
  ordercontainer: {
    height: "70%",
    width: "90%",
  },
  orderscrollcontainer: {
    flex: 1,
  },
});
