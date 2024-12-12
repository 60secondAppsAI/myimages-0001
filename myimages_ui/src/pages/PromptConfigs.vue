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
            <promptConfig-table
            v-if="promptConfigs && promptConfigs.length > 0"
				:title="table1.title"
				:sub-title="table1.subTitle"
				:promptConfigs="promptConfigs"
				:totalElements="totalElements"
				:page="page"
				:columns="table1.columns"
 				@get-all-prompt-configs="getAllPromptConfigs"
             >

            </promptConfig-table>
          </div>
        </card>
      </div>

    </div>
  </div>
</template>
<script>
import { Card } from "@/components/Card";

import PromptConfigTable from "@/components/PromptConfigTable";
import PromptConfigService from "../services/PromptConfigService";

const tableColumns = [];
const tableData = [
];

export default {
  components: {
    Card,
    PromptConfigTable,
  },
  data() {
    return {
      promptConfigs: [],
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
    async getAllPromptConfigs(sortBy='promptConfigId',sortOrder='true',page="0",size="50") {
      try {
        try {
			const searchDTO = {
				sortBy: sortBy, 
				sortOrder: sortOrder, 
				searchQuery: this.searchQuery,
				page: page, 
				size: size
			};
	        let response = await PromptConfigService.getAllPromptConfigs(searchDTO);
	        if (!response.data.empty) {
		        if (response.data.promptConfigs.length) {
					this.promptConfigs = response.data.promptConfigs;
				}
      			this.totalElements = response.data.totalElements;
      			this.page = response.data.page;
	        }
        } catch (error) {
          console.error("Error fetching promptConfigs:", error);
        }
        
      } catch (error) {
        console.error("Error fetching promptConfig details:", error);
      }
    },
  },
  mounted() {
    this.getAllPromptConfigs();
  },
  created() {
    this.$root.$on('searchQueryForPromptConfigsChanged', (searchQuery) => {
    	this.searchQuery = searchQuery;
    	this.getAllPromptConfigs();
    })
  }
};
</script>
<style></style>
