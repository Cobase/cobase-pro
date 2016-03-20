import React, { Component, PropTypes } from 'react';
import ImmutablePropTypes from 'react-immutable-proptypes';
import { Link } from 'react-router';

import SidebarHeader from './SidebarHeader';
import ListItem from './SidebarListItem';
import Title from './SidebarSectionTitle';

export default class Sidebar extends Component {
  render() {
    const { groups } = this.props;

    return (
      <div className="sidebar">
        <SidebarHeader />

        <nav className="sidebar-navigation">
          <Title>Pages</Title>
          <ListItem title="Dashboard" to="/dashboard" icon="fa-list" />

          <Title>Groups</Title>
          <ListItem title="Create new group" to="/groups/add" icon="fa-plus" />

          {groups.map(group =>
            <ListItem
              key={group.id}
              title={group.title}
              to={group.title}
              icon="fa-file-text-o"
            />
          )}

          <Title>User Control</Title>
          <ListItem title="Settings" to="settings" icon="fa-cogs" />
          <ListItem title="Profile" to="profile" icon="fa-user" />
          <a className="sidebar-navigation-item" onClick={this.handleLogout.bind(this)}><i className="fa fa-sign-out fa-fw"></i> Logout</a>
        </nav>
      </div>
    );
  }

  handleLogout(e) {
    e.preventDefault();

    this.props.onLogout();
  }
}

Sidebar.propTypes = {
  onLogout: PropTypes.func.isRequired,
  groups: ImmutablePropTypes.listOf(
    ImmutablePropTypes.contains({
      title: PropTypes.string.isRequired
    })
  )
};
