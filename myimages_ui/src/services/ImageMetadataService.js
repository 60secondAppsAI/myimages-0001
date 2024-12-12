import http from "../http-common"; 

class ImageMetadataService {
  getAllImageMetadatas(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/imageMetadata/imageMetadatas`, searchDTO);
  }
 

  get(imageMetadataId) {
    return this.getRequest(`/imageMetadata/${imageMetadataId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/imageMetadata?field=${matchData}`, null);
  }

  addImageMetadata(data) {
    return http.post("/imageMetadata/addImageMetadata", data);
  }

  update(data) {
  	return http.post("/imageMetadata/updateImageMetadata", data);
  }
  
  uploadImage(data,imageMetadataId) {
  	return http.postForm("/imageMetadata/uploadImage/"+imageMetadataId, data);
  }




	postRequest(url, data) {
		return http.post(url, data);
      };

	getRequest(url, params) {
        return http.get(url, {
        	params: params
        });
    };

}

export default new ImageMetadataService();
