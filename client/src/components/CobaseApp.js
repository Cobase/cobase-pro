import React, { Component } from 'react';
import Sidebar from './Sidebar';

export default class CobaseApp extends Component {
  render() {
    return (
      <div>
        <Sidebar />

        {
          // A Component defined in routes
          this.props.children
        }
      </div>
    );
  }
}
