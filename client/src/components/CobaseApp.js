import React, { Component } from 'react';
import Sidebar from './Sidebar';
import { browserHistory } from 'react-router'

import 'font-awesome/css/font-awesome.css';
import 'normalize.css/normalize.css';
import '../styles/main.less';

export default class CobaseApp extends Component {
  constructor(props) {
    super(props);

    const { user } = this.props;

    if (! user.get('isLoggedIn')) {
      browserHistory.push('/login');
    }
  }

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
