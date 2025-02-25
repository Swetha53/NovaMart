import { StyleSheet, Text, View, Image, Pressable } from "react-native";
import React, { useState } from "react";
import colors from "../config/colors";
import uparrow from "./../assets/up_arrow.png";
import downarrow from "./../assets/down_arrow.png";

const Counter = (props) => {
  const [value, setValue] = useState(1);
  const onChangeEventHandler = (tempValue) => {
    if (value + tempValue <= props.maxQuantity && value + tempValue > 0) {
      setValue(value + tempValue);
      props.onChangeEventHandler(value);
    }
  };
  return (
    <View style={styles.container}>
      <View style={styles.display}>
        <Text>{value}</Text>
      </View>
      <View style={styles.buttons}>
        <Pressable
          onPress={() => {
            onChangeEventHandler(1);
          }}
          style={styles.button}
        >
          <Image
            source={uparrow}
            style={[
              styles.image,
              value == props.maxQuantity ? styles.disabled : {},
            ]}
          />
        </Pressable>
        <Pressable
          onPress={() => {
            onChangeEventHandler(-1);
          }}
          style={styles.button}
        >
          <Image
            source={downarrow}
            style={[styles.image, value == 1 ? styles.disabled : {}]}
          />
        </Pressable>
      </View>
    </View>
  );
};

export default Counter;

const styles = StyleSheet.create({
  container: {
    backgroundColor: colors.secondary,
    display: "flex",
    flexDirection: "row",
    justifyContent: "space-around",
    padding: 5,
    height: 50,
    aspectRatio: 2 / 1,
  },
  display: {
    backgroundColor: colors.primary,
    width: "60%",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  buttons: {
    width: "30%",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  },
  button: {
    height: "50%",
    aspectRatio: 1 / 1,
  },
  image: {
    height: "100%",
    aspectRatio: 1 / 1,
  },
  disabled: {
    pointerEvents: "none",
    opacity: 0.4,
  },
});
