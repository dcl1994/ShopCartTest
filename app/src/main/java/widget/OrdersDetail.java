package widget;

import org.litepal.crud.DataSupport;

/**
 * OrdersDetail entity. @author MyEclipse Persistence Tools
 *
 * 购物车
 *
 */

public class OrdersDetail extends DataSupport{

	// Fields

	private Long id;

	private Long orderId;

	private Long goodsId; // 商品编号

	private Double num;// 已选商品数目

	private Double prices; // 商品总价

	private String goodsUrl;// 商品图片

	private String name; // 商品名称

	private Double price; // 商品单价

	private String description; // 商品描述


	private Double total; //数目num*price=总价

	public boolean isSelect;	//CheckBox的选中状态

	public boolean isSelect() {
		return isSelect;
	}

	public void setIsSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}




	// Constructors

	/** default constructor */
	public OrdersDetail() {
	}

	public OrdersDetail(Long goodsId, Double num, Double prices) {
		super();
		this.goodsId = goodsId;
		this.num = num;
		this.prices = prices;
	}

	/** full constructor */
	public OrdersDetail(Long orderId, Long goodsId, Double num, Double prices) {
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.num = num;
		this.prices = prices;
	}
	public OrdersDetail(Long goodsId, Double num, Double prices,
			String goodsUrl, String name, Double price, String description) {
		super();
		this.goodsId = goodsId;
		this.num = num;
		this.prices = prices;
		this.goodsUrl = goodsUrl;
		this.name = name;
		this.price = price;
		this.description = description;
	}
	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}

	public Double getNum() {
		return this.num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public Double getPrices() {
		return this.prices;
	}

	public void setPrices(Double prices) {
		this.prices = prices;
	}

	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	

}