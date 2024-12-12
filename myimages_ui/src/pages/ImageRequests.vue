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
            <imageRequest-table
            v-if="imageRequests && imageRequests.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:imageRequests="imageRequests"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-image-requests="getAllImageRequests"
             >

            </imageRequest-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import ImageRequestTable from "@/components/ImageRequestTable";
import ImageRequestService from "../services/ImageRequestService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    ImageRequestTable,
  },
  data() {
    return {
      imageRequests: [],
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
    async getAllImageRequests(sortBy='imageRequestId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await ImageRequestService.getAllImageRequests(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.imageRequests.length) {
					this.imageRequests = response.data.imageRequests;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching imageRequests:", error);
        }
        
      } catch (error) {
        console.error("Error fetching imageRequest details:", error);
      }
    },
  },
  mounted() {
    this.getAllImageRequests();
  },
  created() {
    this.$root.$on('searchQueryForImageRequestsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllImageRequests();
    })
  }
};
</script>
<style></style>
