import React, { Component } from 'react';

import 'font-awesome/css/font-awesome.css';
import 'normalize.css/normalize.css';
import '../styles/main.less';

export default class CobaseApp extends Component {
  render() {
    return this.props.children;
  }
}
