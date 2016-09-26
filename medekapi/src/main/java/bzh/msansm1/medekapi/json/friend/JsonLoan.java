package bzh.msansm1.medekapi.json.friend;

public class JsonLoan {
	private Integer id;
	private Integer userId;
	private String userLogin;
	private Integer friendId;
	private String friendLogin;
	private Integer bookId;
	private String book;
	private Integer albumId;
	private String album;
	private Integer movieId;
	private String movie;
	private Integer tvshowId;
	private String tvshow;
	private Long startLong;
	private Long endLong;
	
	public JsonLoan() {
		super();
	}

	public JsonLoan(Integer id, Integer userId, String userLogin, Integer friendId, String friendLogin, Integer bookId,
			String book, Integer albumId, String album, Integer movieId, String movie, Integer tvshowId, String tvshow,
			Long startLong, Long endLong) {
		super();
		this.id = id;
		this.userId = userId;
		this.userLogin = userLogin;
		this.friendId = friendId;
		this.friendLogin = friendLogin;
		this.bookId = bookId;
		this.book = book;
		this.albumId = albumId;
		this.album = album;
		this.movieId = movieId;
		this.movie = movie;
		this.tvshowId = tvshowId;
		this.tvshow = tvshow;
		this.startLong = startLong;
		this.endLong = endLong;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public Integer getFriendId() {
		return friendId;
	}

	public void setFriendId(Integer friendId) {
		this.friendId = friendId;
	}

	public String getFriendLogin() {
		return friendLogin;
	}

	public void setFriendLogin(String friendLogin) {
		this.friendLogin = friendLogin;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public Integer getAlbumId() {
		return albumId;
	}

	public void setAlbumId(Integer albumId) {
		this.albumId = albumId;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public Integer getTvshowId() {
		return tvshowId;
	}

	public void setTvshowId(Integer tvshowId) {
		this.tvshowId = tvshowId;
	}

	public String getTvshow() {
		return tvshow;
	}

	public void setTvshow(String tvshow) {
		this.tvshow = tvshow;
	}

	public Long getStartLong() {
		return startLong;
	}

	public void setStartLong(Long startLong) {
		this.startLong = startLong;
	}

	public Long getEndLong() {
		return endLong;
	}

	public void setEndLong(Long endLong) {
		this.endLong = endLong;
	}
	
}
