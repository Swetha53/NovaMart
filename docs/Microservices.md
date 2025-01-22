# User (Authentication)
1. **Programming Language**: Java
2. **Database**:
   1. **User Data**: MongoDB
   2. **Session Storage**: Redis
3. **REST API**:
   1. /users/register
   2. /users/login
   3. /users/profile
4. **Responsibilities**:
   1. Manage user accounts that includes user type.
   2. Verify user using email and password.
   3. Manage user profile, preference and setting.
5. **Database Schema**:
   1. user_id
   2. email
   3. password
   4. first name
   5. last name
   6. age
   7. gender
   8. address
   9. created at
   10. avatar url
   11. phone number
   12. category preference
   13. roles
   14. preferences
       1. Theme
       2. Notifications

# Product
1. **Programming Language**: Node JS/Java
2. **Database**: MongoDB
3. **REST API**:
   1. /products
   2. /products/{ID}
   3. /products/create
4. **Responsibilities**:
   1. Shows accurate product details [Inventory]
   2. Manages product catalog including their status
   3. Manages product prices updating in real time
   4. Shows users only approved products and merchants all the products
5. **Database Schema**:
   1. product_id
   2. name
   3. description
   4. price
   5. currency code
   6. categories
   7. reviews
      1. user_id
      2. rating
      3. comments
   8. created at
   9. updated at
   10. status
   11. attributes

# Cart
1. **Programming Language**: Node JS
2. **Database**: SQL
3. **REST API**:
   1. /cart/update
   2. /cart
4. **Responsibilities**:
   1. Add/update/remove items for cart
   2. Cart saved across sessions
   3. Ensure up-to-date pricing [Product]
   4. Validate product availability and reserve stock when added to cart [Inventory]
   5. Apply relevant promotions/discount [OPTIONAL]
   6. Clean up old/expired products from cart
5. **Database Schema**:
   1. **Cart**
      1. cart_id
      2. user_id
      3. created at
      4. updated at
      5. expires at
   2. **Cart Items**
      1. cart_item_id
      2. cart_id
      3. product_id
      4. quantity
      5. unit price
      6. total price
      7. added at

# Payment (Checkout)
1. **Programming Language**: Java
2. **Database**: MySQL
3. **REST API**:
   1. /payments/initiate
   2. /payments/status
4. **Responsibilities**:
   1. Handles payment initiation and verification
   2. Stores payment details
   3. Handles refunds/payments failures
5. **Database Schema**:
   1. Payment
      1. payment_id
      2. user_id
      3. order_id
      4. amount
      5. currency code
      6. status
      7. payment method
      8. created at
      9. updated at
   2. Refund
      1. refund_id
      2. payment_id
      3. amount
      4. status
      5. reason
      6. currency code
      7. created at
      8. updated at

# Order
1. **Programming Language**: Java
2. **Database**: MySQL
3. **REST API**:
   1. /orders
   2. /orders/{ID}
4. **Responsibilities**:
   1. Processes orders and tracks order status
   2. Stores order details
5. **Database Schema**:
   1. Order
      1. order_id
      2. user_id
      3. payment_id
      4. status
      5. total amount
      6. currency code
      7. created at
      8. updated at
   2. Order Item
      1. order_item_id
      2. order_id
      3. product_id
      4. quantity
      5. unit price
      6. total price
      7. created at
   3. Order Status History
      1. status_history_id
      2. order_id
      3. old status
      4. new status
      5. changed at

# Inventory
1. **Programming Language**: Java
2. **Database**: MySQL
3. **REST API**:
   1. /inventory/{ID}
   2. /inventory/reserve/{ID}
   3. /inventory/update/{ID}
4. **Responsibilities**:
   1. Tracks quantity of products and provides products availability
   2. Reserved stock for pending orders and handling expired/cancelling reservation [Order/Cart]
   3. Updates stock when order confirmed [Order]
5. **Database Schema**:
   1. Inventory
      1. inventory_id
      2. product_id
      3. quantity available
      4. quantity reserved
      5. last updated
   2. Reservation
      1. reservation_id
      2. inventory_id
      3. order_id
      4. quantity reserved
      5. reserved at

# Search
1. Elastic Search
2. **Responsibilities**
   1. Periodically index data from other services [Product/Order]
   2. Process query and optimize search

# Notification
1. **Programming Language**: Python/Node JS
2. **Database**:
   1. Event based messaging with **Rabbit MQ**
   2. Message Storage: MongoDB

# AR
1. **Programming Language**:
   1. Three JS
   2. Python (Pytorch 3D)
2. **Database**: MongoDB
3. **REST API**:
   1. /reality/{ID}
4. **Responsibilities**:
   1. Render 3D model to reality
   2. If product model already available render it
   3. Else if no product model then create using images [Product]
5. **Database Schema**:
   1. asset_id
   2. name
   3. type
   4. url
   5. category
   6. dimensions