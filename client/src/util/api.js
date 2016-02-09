import axios from 'axios';

const BASE_URL = 'http://cobase.tunk.io';

export default {
  login(credentials) {
    console.log('sending login credentials...');
    return axios.post(`${BASE_URL}/api/login`, credentials);
  }
}
