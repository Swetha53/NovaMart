import "./OrderTable.scss";
import Button from "./../Button/Button";
import Image from "./../../assets/image.png";

// {
//     "orderId": "cb55c108-af1a-4a13-9169-15226cfd7253",
//     "userId": "9633ec2b-7e0e-466e-866a-159afccf7542",
//     "orderStatus": "DELIVERED",
//     "totalAmount": 230.00,
//     "currencyCode": "CAD",
//     "createdAt": 1738788348035,
//     "orderItemList": [
//         {
//             "orderItemId": "757eecad-0e5d-4f71-8e85-e8c52f4350c6",
//             "orderId": "cb55c108-af1a-4a13-9169-15226cfd7253",
//             "productId": "eb7ab6de-aafd-4023-90b9-3106dddeadc8",
//             "merchantId": "7f6852dc-ee5d-4ad4-8a26-7ed381cfdd63",
//             "quantity": 2,
//             "unitPrice": 50.00,
//             "totalPrice": 100.00,
//             "createdAt": 1738788348036
//         }
//     ]
// }

function OrderTable(props) {
  const getProductSupport = () => {
    console.log("Get Product Support");
  };
  const writeProductReview = () => {
    console.log("Write Product Review");
  };

  return (
    <div className="order">
      <div className="order__header">
        <div className="order__header__left">
          <span className="header1">Order Placed</span>
          <span className="createdat">{props.order.createdAt}</span>
          <span className="header2">Total</span>
          <span className="totalamount">
            {props.order.currnecyCode} {props.order.totalAmount}
          </span>
          <span className="header3">Ship To</span>
          <span className="address">{props.name}</span>
        </div>
        <Button
          text="Get Product Support"
          onClickHandler={getProductSupport}
          width="20%"
          margin="0rem"
          height="1.5rem"
          reverse={true}
        />
        <div className="order__header__right">
          <span className="orderid">Order #{props.order.orderId}</span>
          <a className="orderdetail">View Order Detail</a>
          <a className="print">Print Invoice</a>
        </div>
      </div>
      <div className="order__items">
        {props.order.orderItemList.map((item, index) => (
          <div key={index} className="order__items__item">
            <div className="image">
              <img src={Image} alt="Product Image" />
            </div>
            <div className="name">
              <a>{item.productId}</a> (x{item.quantity})
            </div>
            <div className="amount">
              {props.order.currencyCode} {item.totalPrice}
            </div>
            <Button
              text="Write Product Review"
              onClickHandler={writeProductReview}
              width="80%"
              margin="0rem"
              height="1.5rem"
            />
          </div>
        ))}
      </div>
    </div>
  );
}

export default OrderTable;
