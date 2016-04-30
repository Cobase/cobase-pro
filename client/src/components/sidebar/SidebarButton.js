import React, { PropTypes } from 'react';
import { Link } from 'react-router';

function SidebarSectionButton({ to, icon }) {
  const iconClasses = `fa ${icon} fa-fw`;

  return (
    <Link
      to={to}
      className="sidebar-navigation-button"
    >
      <i className={iconClasses} />
    </Link>
  );
}

SidebarSectionButton.propTypes = {
  to: PropTypes.string.isRequired,
  icon: PropTypes.string.isRequired,
};

export default SidebarSectionButton;
