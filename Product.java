package com.anand.product.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity

public class Product 
{
	    @Id
	    private Long id;
	    private String name;
		private double price;
		private String discount;
	    private double totleprice;

		public Product() {
			super();
		}
		public Product(Long id, String name, double totleprice, String discount, double price) {
			super();
			this.id = id;
			this.name = name;
			this.totleprice = totleprice;
			this.discount = discount;
			this.price = price;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public double getTotleprice() {
			return totleprice;
		}
		public void setTotleprice(double totleprice) {
			this.totleprice = totleprice;
		}
		public String getDiscount() {
			return discount;
		}
		public void setDiscount(String discount) {
			this.discount = discount;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		@Override
		public String toString() {
			return "Product [id=" + id + ", name=" + name + ", totleprice=" + totleprice + ", discount=" + discount
					+ ", price=" + price + "]";
		}
		
      
	}



