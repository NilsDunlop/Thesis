import clang.cindex
from clang.cindex import CursorKind
import static_method_visitor
import static_field_visitor
import data_member_visitor

# Function to extract methods from a source file
def method_extraction(source_file):
    # Create an index object
    index = clang.cindex.Index.create()

    # Parse the source file
    translation_unit = index.parse(source_file)

    # Iterate over the AST nodes
    count = 0
    for node in translation_unit.cursor.walk_preorder():
        # Check if the node is a C++ method or function
        if node.kind == CursorKind.CXX_METHOD or node.kind == CursorKind.FUNCTION_DECL:
            count += 1
            method_list.append(node)

    return count

# Function to extract fields from a source file
def field_extraction(source_file):
    # Create an index object
    index = clang.cindex.Index.create()

    translation_unit = index.parse(source_file)

    # Iterate over the AST nodes
    count = 0
    for node in translation_unit.cursor.walk_preorder():
        # Check if the node is a C++ field or variable
        if node.kind == CursorKind.FIELD_DECL or node.kind == CursorKind.VAR_DECL:
            count += 1
            field_list.append(node)
    return count

# Function to recursively extract private constructors from a node
def extract_private_constructors(node):
    if node.kind == CursorKind.CLASS_DECL or CursorKind.CLASS_TEMPLATE:
        for child in node.get_children():
            if child.kind == CursorKind.CONSTRUCTOR and (
                    child.access_specifier == clang.cindex.AccessSpecifier.PRIVATE or child.access_specifier == clang.cindex.AccessSpecifier.PROTECTED):
                private_constructors_list.append(child)

    for child in node.get_children():
        extract_private_constructors(child)

# Function to detect abstract methods in a source file
def detect_abstract_methods(source_file):
    index = clang.cindex.Index.create()
    translation_unit = index.parse(source_file)

    def is_abstract_method(cursor):
        return (cursor.kind == CursorKind.CXX_METHOD and
                cursor.is_virtual_method() and
                cursor.is_pure_virtual_method())

    abstract_methods = [cursor for cursor in translation_unit.cursor.walk_preorder() if is_abstract_method(cursor)]
    count = len(abstract_methods)

    return abstract_methods, count

# Function to check if a cursor represents a pure virtual method
def is_pure_virtual_method(cursor):
    return cursor.kind == clang.cindex.CursorKind.CXX_METHOD and cursor.is_pure_virtual_method()

# Function to check if a cursor represents an interface class
def is_interface_class(cursor):
    if cursor.kind != clang.cindex.CursorKind.CLASS_DECL:
        return False

    has_pure_virtual_method = False
    for child in cursor.get_children():
        if is_pure_virtual_method(child):
            has_pure_virtual_method = True
        elif child.kind not in (
        clang.cindex.CursorKind.CXX_ACCESS_SPEC_DECL, clang.cindex.CursorKind.CXX_BASE_SPECIFIER):
            return False

    return has_pure_virtual_method

# Function to find interfaces in a source file
def find_interfaces(source_file):
    index = clang.cindex.Index.create()
    translation_unit = index.parse(source_file)

    interfaces = []
    for cursor in translation_unit.cursor.walk_preorder():
        if is_interface_class(cursor):
            interfaces.append(cursor)

    count = len(interfaces)

    return interfaces, count

# Function to get overridden methods for a given cursor
def get_overridden_methods(cursor):
    def traverse(node):
        if node.kind == clang.cindex.CursorKind.CXX_METHOD and node.is_virtual_method():
            for child in node.get_children():
                if child.kind == clang.cindex.CursorKind.CXX_OVERRIDE_ATTR:
                    override_list.append(node)
                    break
        for child in node.get_children():
            traverse(child)

    traverse(cursor)
    return override_list

# Function to find classes that have fields of their own type
def find_classes_with_own_type_fields(cursor, results):
    for node in cursor.walk_preorder():
        if node.kind == CursorKind.FIELD_DECL:
            if node.semantic_parent.spelling == node.type.spelling[:-2]:
                results.append(node)
            elif node.semantic_parent.spelling == node.type.spelling:
                results.append(node)

    return results

# List of source files to process
source_files = ["C:\\Users\\nilsd\\Desktop\\C++\\ClickHouse\\src\\Columns\\ColumnDecimal.cpp"]

for source_file in source_files:
    print(f"\nProcessing {source_file}")
    method_list, field_list, private_constructors_list, override_list, field_own_type_list = [], [], [], [], []

    index = clang.cindex.Index.create()
    tu = index.parse(source_file)
    method_count = method_extraction(source_file)
    field_count = field_extraction(source_file)
    static_methods, static_method_count = static_method_visitor.extract_static_methods(source_file)
    static_fields, static_field_count = static_field_visitor.extract_static_fields(source_file)
    abstract_methods, abstract_method_count = detect_abstract_methods(source_file)
    interfaces, interfaces_count = find_interfaces(source_file)
    overriden_methods = get_overridden_methods(tu.cursor)
    data_members, data_member_count = data_member_visitor.detect_data_members(source_file)
    find_classes_with_own_type_fields(tu.cursor, field_own_type_list)

    # Print results for the current source file

    print("\n-----------------------------\n")

    print(f"Number of methods: {method_count}")
    for method in method_list:
        print(f"{method.spelling} at {method.location}")

    print(f"\nNumber of static_methods: {static_method_count}")

    # Print the list of static methods
    for name, return_type, parameters in static_methods:
        param_str = ', '.join(parameters)
        print(f'{return_type} {name}({param_str});')

    print(f"\nNumber of fields: {field_count}")

    for node in field_list:
        print(f"{node.type.spelling} {node.spelling} at {node.location}")

    print(f"\nNumber of static fields: {static_field_count}")
    for name, type in static_fields:
        print(f'{type} {name};')

    extract_private_constructors(tu.cursor)
    print(f"\nNumber of private constructors: {len(private_constructors_list)}")
    for child in private_constructors_list:
        print(f"Private constructor: {child.displayname} at {child.location}")

    print(f"\nNumber of abstract methods: {abstract_method_count}")
    for method in abstract_methods:
        print(f"{method.spelling} at {method.location}")

    print(f"\nNumber of interfaces: {interfaces_count}")
    for interface in interfaces:
        print(f"{interface.spelling} at {interface.location}")

    print(f"\nNumber of override methods: {len(overriden_methods)}")
    for override in overriden_methods:
        print(f"{override.spelling} at {override.location} in class {override.semantic_parent.spelling}")

    print(f"\nNumber of data members: {data_member_count}")
    for node in data_members:
        print(f"{node.type.spelling} {node.spelling} at {node.location}")

    print(f"\nNumber of classes with fields of own type: {len(field_own_type_list)}")
    for node in field_own_type_list:
        print(f"{node.type.spelling} {node.spelling} {node.location}")