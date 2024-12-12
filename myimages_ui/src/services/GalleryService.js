import http from "../http-common"; 

class GalleryService {
  getAllGallerys(searchDTO) {
    console.log(searchDTO)
    return this.getRequest(`/gallery/gallerys`, searchDTO);
  }
 

  get(galleryId) {
    return this.getRequest(`/gallery/${galleryId}`, null);
  }

  findByField(matchData) {
    return this.getRequest(`/gallery?field=${matchData}`, null);
  }

  addGallery(data) {
    return http.post("/gallery/addGallery", data);
  }

  update(data) {
  	return http.post("/gallery/updateGallery", data);
  }
  
  uploadImage(data,galleryId) {
  	return http.postForm("/gallery/uploadImage/"+galleryId, data);
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

export default new GalleryService();
