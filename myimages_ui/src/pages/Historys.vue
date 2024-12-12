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
            <history-table
            v-if="historys && historys.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:historys="historys"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-historys="getAllHistorys"
             >

            </history-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import HistoryTable from "@/components/HistoryTable";
import HistoryService from "../services/HistoryService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    HistoryTable,
  },
  data() {
    return {
      historys: [],
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
    async getAllHistorys(sortBy='historyId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await HistoryService.getAllHistorys(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.historys.length) {
					this.historys = response.data.historys;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching historys:", error);
        }
        
      } catch (error) {
        console.error("Error fetching history details:", error);
      }
    },
  },
  mounted() {
    this.getAllHistorys();
  },
  created() {
    this.$root.$on('searchQueryForHistorysChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllHistorys();
    })
  }
};
</script>
<style></style>
