package com.example.demo.model;

import java.util.List;

public class Search {
	
		 
	    private Integer zip;
	    private String description;
	    private Integer beds ;
	    private Integer baths ;
	    private Integer sqft ;
	    private List<String> amenities;
	    private String houseType;
	    
	    public Integer getZip() {
	        return zip;
	    }
	 
	    public void setZip(Integer id) {
	        this.zip = id;
	    }
	 
	    public String getDescription() {
	        return description;
	    }
	 
	    public void setDescription(String firstname) {
	        this.description = firstname;
	    }

		public Integer getBeds() {
			return beds;
		}

		public void setBeds(Integer beds) {
			this.beds = beds;
		}

		public Integer getBaths() {
			return baths;
		}

		public void setBaths(Integer bath) {
			this.baths = bath;
		}

		public Integer getSqft() {
			return sqft;
		}

		public void setSqft(Integer sqft) {
			this.sqft = sqft;
		}

		public List<String> getAmenities() {
			return amenities;
		}

		public void setAmenities(List<String> amenities) {
			this.amenities = amenities;
		}
	 
	    
		public String toString() {
			StringBuilder returnStr = new StringBuilder("");
			returnStr.append("\nZipCode: ").append(zip.toString());
			returnStr.append("\nNumber of beds: ").append(beds.toString());
			returnStr.append("\nNumber of baths: ").append(baths.toString());
			returnStr.append("\nSQFT: ").append(sqft.toString());
			return returnStr.toString();
			
		}

		public String getHouseType() {
			return houseType;
		}

		public void setHouseType(String houseType) {
			this.houseType = houseType;
		}
	 
	}