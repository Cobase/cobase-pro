import React, { Component } from 'react';
import Sidebar from './Sidebar';

export default class LoggedIn extends Component {
  render() {
    const { children, groups, currentUser, onLogout } = this.props;

    return (
      <div className="logged-in">
        <Sidebar onLogout={onLogout} groups={groups} />

        {children && React.cloneElement(children, {
          currentUser
        })}
      </div>
    );
  }
}
