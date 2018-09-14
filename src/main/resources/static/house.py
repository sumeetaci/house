class house:

	"""A customer of ABC Bank with a checking account. Customers have the
	following properties:

	Attributes:
		title: A title for the link.
	price: Price asked in the posting.
	
	"""
	# house_ins = new house(link,name,price_apt,bedbathsoup.text,sqftsoup,location[0].get('data-latitude'), location[0].get('data-longitude'),''.join(txt_attr_utils),  availabledatesoup.text, desc)
	def __init__(self, link, ttitle, price, bed_bath, sqft, latitude, longitude, utilities, available_date, desc):
		self.link = link
		self.title = ttitle
		self.price = price
		self.bed_bath = bed_bath
		self.sqft = sqft
		self.longitude = longitude
		self.latitude = latitude
		self.available_date = available_date
		self.utilities = utilities
		self.desc = desc
	
	def __init__(self, link):
		self.link = link
		
	def setTitle(self, ttitle):
		self.title = ttitle
		
	def setPrice(self, price):
		self.price = price
	
	def setBedBath(self, bed_bath):
		self.bed_bath = bed_bath
	
	def setSqft(self, sqft):
		self.sqft = sqft
	
	def setLongitude(self, longitude):
		self.longitude = longitude
		
	def setLatitutde(self, latitude):
		self.latitude = latitude
	
	def setAvailability(self, available_date):
		self.available_date = available_date
		
	def setUtilities(self, utilities):
		self.utilities = utilities
		
	def setDesc(self, desc):
		self.desc = desc
			
	
		
	def getLink(self):
		"""Return the price."""
		return self.link

	def getTitle(self):
		"""Return the price."""
		return self.title

	def getBedBath(self):
		"""Return the bed_bath."""
		return self.bed_bath

	
	def getPrice(self):
		"""Return the price."""
		return self.price

	def getAvailablity(self):
		"""Return the available_date."""
		return self.available_date

	def getSqft(self):
		"""Return the sqft."""
		return self.sqft


	def getLongitude(self):
		"""Return the longitude."""
		return self.longitude
		
		
	def getLatitude(self):
		"""Return the latitude."""
		return self.latitude
		
	def getUtils(self):
		"""Return the utilities."""
		return self.utilities
	
	def getDesc(self):
		"""Return the desc."""
		return self.desc