<template>
  <div class="content">
    <div class="row">
      <div class="col-12">
        <card class="card-plain">
          <!-- <template slot="header">
            <h4 class="card-title">Table on Plain Background</h4>
            <p class="category">Here is a subtitle for this table</p>
          </template>-->
          <div class="table-full-width text-left">
            <gallery-table
            v-if="gallerys && gallerys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:gallerys="gallerys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-gallerys="getAllGallerys"
             >

            </gallery-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import GalleryTable from "@/components/GalleryTable";
import GalleryService from "../services/GalleryService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    GalleryTable,
  },
  data() {
    return {
      gallerys: [],
	  totalElements: 0,
      page: 0,
      searchQuery: '',     
      table1: {
        title: "Simple Table",
        columns: [...tableColumns],
        data: [...tableData],
      },
    };
  },
  methods: {
    async getAllGallerys(sortBy='galleryId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await GalleryService.getAllGallerys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.gallerys.length) {
					this.gallerys = response.data.gallerys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching gallerys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching gallery details:", error);
      }
    },
  },
  mounted() {
    this.getAllGallerys();
  },
  created() {
    this.$root.$on('searchQueryForGallerysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllGallerys();
    })
  }
};
</script>
<style></style>
