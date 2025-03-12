import "./OrderTable.scss";
import Button from "./../Button/Button";
import Image from "./../../assets/image.png";

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
