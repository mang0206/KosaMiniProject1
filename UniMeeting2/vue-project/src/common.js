import {
    reactive,
    computed,
    toRefs
  } from 'vue';
  
  import axios from 'axios';
  
  
  const api = async (url, method, data) => {
    return (await axios({
      method: method,
      url,
      data
    }).catch(e => {
      console.log(e);
    })).data;
  }
  
  export {
    api
  };