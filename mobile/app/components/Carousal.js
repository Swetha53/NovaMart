import { StyleSheet, Image, SafeAreaView, Pressable } from "react-native";
import React, { useState } from "react";
import colors from "../config/colors";
import GestureRecognizer from "react-native-swipe-gestures";

const Carousal = (props) => {
  const { images } = props;
  const [index, setIndex] = useState(0);
  const updateImageIndex = (value) => {
    if (index + value < 0) {
      setIndex(images.length - 1);
    } else if (index + value == images.length) {
      setIndex(0);
    } else {
      setIndex(index + value);
    }
  };
  const goToImageIndex = (value) => {
    console.log(value);
    setIndex(value);
  };
  return (
    <SafeAreaView style={styles.container}>
      <GestureRecognizer
        onSwipeLeft={() => updateImageIndex(1)}
        onSwipeRight={() => updateImageIndex(-1)}
      >
        <Image source={{ uri: images[index] }} style={styles.image}></Image>
      </GestureRecognizer>
      <SafeAreaView style={styles.minicontainer}>
        {images.map((image, idx) => (
          <Pressable
            key={"child-" + idx}
            onPress={() => {
              goToImageIndex(idx);
            }}
          >
            <Image
              source={{ uri: image }}
              style={[
                styles.miniimage,
                idx !== index ? styles.passiveimage : {},
              ]}
            ></Image>
          </Pressable>
        ))}
      </SafeAreaView>
    </SafeAreaView>
  );
};

export default Carousal;

const styles = StyleSheet.create({
  container: {
    width: "100%",
    height: "40%",
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
  },
  image: {
    height: "80%",
    aspectRatio: 1 / 1,
    borderRadius: "10%",
  },
  minicontainer: {
    height: "20%",
    width: "100%",
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-around",
    alignItems: "center",
  },
  miniimage: {
    height: "100%",
    aspectRatio: 1 / 1,
    borderRadius: "10%",
  },
  passiveimage: {
    opacity: 0.4,
  },
});
