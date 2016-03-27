import React, { Component } from 'react';
import Topbar from './Topbar';
import AddGroupForm from './AddGroupForm';

export default class AddGroup extends Component {
  render() {
    return (
      <div className="main">
        <Topbar title="Add Group" />

        <div className="add-group-container">
          <section className="add-group">
            <AddGroupForm onAddGroup={this.props.onAddGroup} />
          </section>
        </div>
      </div>
    );
  }
}
