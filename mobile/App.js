import { StatusBar } from "expo-status-bar";
import { useDeviceOrientation } from "@react-native-community/hooks";
import { NavigationContainer } from "@react-navigation/native";
import { createNativeStackNavigator } from "@react-navigation/native-stack";
import Product from "./app/screens/Product";
import Profile from "./app/screens/Profile";

const Stack = createNativeStackNavigator();

export default function App() {
  const { landscape } = useDeviceOrientation();

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Profile">
        <Stack.Screen name="Product" component={Product} />
        <Stack.Screen name="Profile" component={Profile} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
