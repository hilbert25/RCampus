function stringPredetail(str){
	str = str.replace(/\%/g, "%25");
	str = str.replace(/\+/g, "%2B");
	str = str.replace(/\&/g, "%26");
	return str;
}