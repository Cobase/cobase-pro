import React, { Component } from 'react';
import Sidebar from './Sidebar';

import 'normalize.css/normalize.css';
import '../styles/main.less';

export default class CobaseApp extends Component {
  render() {
    return (
      <div className="app">
        <Sidebar />

        {
          // A Component defined in routes
          this.props.children
        }
      </div>
    );
  }
}
